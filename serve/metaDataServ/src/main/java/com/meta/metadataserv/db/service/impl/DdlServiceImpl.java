package com.meta.metadataserv.db.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.dao.DdlDao;
import com.meta.metadataserv.db.dao.SqlDao;
import com.meta.metadataserv.db.service.IDbColumnService;
import com.meta.metadataserv.db.service.IDbTableService;
import com.meta.metadataserv.db.service.IDdlService;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.enums.TableEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DdlServiceImpl implements IDdlService {
    @Resource
    private DdlDao ddlDao;

    @Resource
    private SqlDao sqlDao;

    @Resource
    private IDbTableService dbTableService;

    @Resource
    private IDbColumnService dbColumnService;

    /**
     * 创建临时表 .
     * @param tableName .
     */
    public String createTempTable(String tableName) {
        TableEnum tableEnum = TableEnum.toEnum(tableName);
        return createTempTable(tableEnum);
    }

    /**
     * 创建临时表 .
     * @param tableEnum .
     */
    public String createTempTable(TableEnum tableEnum) {
        ddlDao.createTempTable(tableEnum.getTableName());
        return tableEnum.getTempTableName();
    }

    /**
     * 删除临时表，再重新创建 .
     * @param tableEnum
     * @return
     */
    public String createTempTableForce(TableEnum tableEnum) {
        dropTempTable(tableEnum.getTableName());
        return createTempTable(tableEnum);
    }

    /**
     * 删除表
     * @param tableName
     */
    public void dropTable(String tableName) {
        ddlDao.dropTable(tableName);
    }

    /**
     * 删除临时表 .
     * @param tableName
     */
    public void dropTempTable(String tableName) {
        TableEnum tableEnum = TableEnum.toEnum(tableName);
        dropTable(tableEnum.getTempTableName());
    }

    /**
     * 通过动态sql创建表 .
     * @param sql
     */
    public void createTableByDynamicSql(String sql) {
        sqlDao.execDynamicSql(sql);
    }

    /**
     * 生成建表语句 .
     * @param tableName
     * @param schema
     * @return
     */
    public String getCreateTableSql(String tableName, String schema) {
        StringBuilder sql = new StringBuilder();

        TableQueryCond tableQueryCond = new TableQueryCond();
        tableQueryCond.setTableName(tableName);
        tableQueryCond.setSchema(schema);
        Page<DbTable> tablePage = dbTableService.getDbTable(tableQueryCond);
        if (tablePage.getTotal() == 0) {
            throw new RuntimeException(schema + "库中未查询到表" + tableName);
        }
        DbTable table = tablePage.getRecords().get(0);


        sql.append("create table if not exists " + tableName + " (");
        List<DbColumn> columnList = dbColumnService.getDbColumn(tableName, schema);
        for (DbColumn column : columnList) {
            sql.append(column.getColumnName() + " ");
            sql.append(column.getDataType());
            Long columnSize = column.getVarcharLength() == null ? column.getNumberLength() : column.getVarcharLength();
            if (columnSize != null) {
                sql.append("(" + columnSize);
                Long columnScale = column.getNumberScale();
                if (columnScale != null && columnScale.longValue() > 0) {
                    sql.append("," + columnScale);
                }
                sql.append(") ");
            }
            if (1 == column.getIsPrimary()) {
                sql.append(" primary key ");
            }
            if (StringUtils.isNotEmpty(column.getExtra())) {
                if (StringUtils.isEmpty(column.getColumnDefault())) {
                    sql.append(" " + column.getExtra() + " ");
                }
            }
            if (StringUtils.isNotEmpty(column.getColumnDefault())) {
                sql.append(" default " + column.getColumnDefault());
            }
            if (StringUtils.isNotEmpty(column.getRemark())) {
                sql.append(" comment " + "'" + column.getRemark() + "',");
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        if (StringUtils.isNotEmpty(table.getRemark())) {
            sql.append(" ) comment " + "'" + table.getRemark() + "'");
        }

        return sql.toString();
    }
}
