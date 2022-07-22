package com.meta.metadataserv.db.service.impl;


import com.alibaba.druid.support.calcite.DDLColumn;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.DbManagerDao;
import com.meta.metadataserv.db.dao.DbTableDao;
import com.meta.metadataserv.db.service.IDbManagerService;
import com.meta.metadataserv.db.service.IDbTableService;
import com.meta.metadataserv.db.service.IDdlService;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.enums.TableEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.meta.metadataserv.enums.TableEnum.T_METADATA_DB_TABLE;

@Service
public class DbTableServiceImpl extends ServiceImpl<DbTableDao, DbTable> implements IDbTableService {
    @Resource
    private IDdlService ddlService;

    /**
     * 通过元数据拉取表，进行保存
     * @param dbConf
     */
    public void syncTable(DbConf dbConf) {
        List<DbTable> tableList = getBaseMapper().getMysqlAllTables(dbConf.getDbSchema());
        saveDbTable(tableList);
    }

    /**
     * 保存表 .
     * @param tableList
     */
    public void saveDbTable(List<DbTable> tableList) {
        //保存数据库中获取的表，增量增加，仅做Insert操作，已存在的表不做更新处理
        String tempTableName = ddlService.createTempTableForce(T_METADATA_DB_TABLE);
        getBaseMapper().insertIntoDbTableTemp(tableList);
        getBaseMapper().saveTempToDbTable();
        ddlService.dropTable(tempTableName);
    }

    /**
     * 通过条件查询表 .
     * @param queryCond
     * @return
     */
    public Page<DbTable> getDbTable(TableQueryCond queryCond) {
        Integer currentPage = queryCond.getCurrentPage() == null ? 1 : queryCond.getCurrentPage();
        Integer size = queryCond.getSize() == null ? 20 : queryCond.getSize();
        Page page = new Page(currentPage, size);
        Page<DbTable> result = getBaseMapper().getDbTable(page, queryCond);
        return result;
    }
}
