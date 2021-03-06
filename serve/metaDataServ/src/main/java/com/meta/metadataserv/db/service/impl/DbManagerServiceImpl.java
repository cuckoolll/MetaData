package com.meta.metadataserv.db.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.DbConfDao;
import com.meta.metadataserv.db.dao.DbManagerDao;
import com.meta.metadataserv.db.service.*;
import com.meta.metadataserv.domain.model.*;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.enums.DbType;
import com.meta.metadataserv.enums.Procedure;
import com.meta.metadataserv.utils.SqlUtil;
import com.meta.metadataserv.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class DbManagerServiceImpl extends ServiceImpl<DbManagerDao, DbConf> implements IDbManagerService {
    @Resource
    private IDbTableService dbTableService;

    @Resource
    private IDbColumnService dbColumnService;

    @Resource
    private IDbIndexService dbIndexService;

    @Resource
    private IDbService dbService;

    /**
     * 获取数据库连接 .
     * @param dbConf
     * @return
     */
    private Connection getConnection(DbConf dbConf) {
        final String dbType = dbConf.getDbType();
        if (StringUtils.isEmpty(dbType)) {
            throw new RuntimeException("未发现数据类型");
        }
        DbType dbTypeEnum = DbType.valueOf(dbType.toLowerCase());
        Connection connection = null;
        try {
            //获取驱动
            Class.forName(dbTypeEnum.getDrive());
        } catch (Exception e) {
            throw new RuntimeException("未发现数据库驱动");
        }
        try {
            Properties info = new Properties();
            // 填入你的用户名、密码和连接地址
            info.put("user", dbConf.getDbUsername());
            info.put("password", dbConf.getDbPassword());
            String jdbcUrl = dbTypeEnum.getDbUrl(dbConf.getDbUrl(), dbConf.getDbSchema());
            connection = DriverManager.getConnection(jdbcUrl, info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * 测试连接 .
     * @param dbConf ,
     */
    public void testConnection(DbConf dbConf) {
        final Connection connection = getConnection(dbConf);
//            connection.close();
    }

    /**
     * 获取元数据信息 .
     * @param connection
     * @return
     */
    private DatabaseMetaData getMetaData(Connection connection) {
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
        } catch (Exception e) {
            throw new RuntimeException("获取元数据信息失败");
        }
        return metaData;
    }

    /**
     * 同步数据库元数据信息 。
     * @param dbConf 。
     */
    public void syncMetaData(DbConf dbConf) {
        //同步表
        dbTableService.syncTable(dbConf);

        //同步字段
        dbColumnService.syncColumn(dbConf);

        //同步索引
        dbIndexService.syncIndex(dbConf);

        //更新数据库时间
        dbService.updateTime(dbConf.getDbSchema());
    }

    /**
     * 导出创建表sql .
     * @param tableId
     * @return
     */
    public String exportTableSql(String tableId) {
        DbTable table = dbTableService.getById(tableId);
        String tableName = table.getTableName();
        String tableSchema = table.getTableSchema();
        String remark = table.getRemark();

        List<DbColumn> columnList = dbColumnService.getDbColumn(tableName, tableSchema);
        List<DbIndex> indexList = dbIndexService.getDbIndex(tableName, tableSchema);

        return SqlUtil.buildCreateTableSql(tableName, remark, ColumnVo.listOf(columnList), IndexVo.listOf(indexList));
    }
}
