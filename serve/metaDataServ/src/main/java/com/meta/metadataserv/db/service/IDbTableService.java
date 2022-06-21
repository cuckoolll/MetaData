package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.TableQueryCond;

import java.util.List;

public interface IDbTableService extends IService<DbTable>  {
    /**
     * 通过元数据拉取表，进行保存
     * @param dbConf
     */
    void syncTable(DbConf dbConf);

    /**
     * 保存表 .
     * @param tableList
     */
    void saveDbTable(List<DbTable> tableList);

    /**
     * 通过条件查询表 .
     * @param queryCond
     * @return
     */
    Page<DbTable> getDbTable(TableQueryCond queryCond);
}
