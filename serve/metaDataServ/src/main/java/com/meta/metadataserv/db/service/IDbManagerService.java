package com.meta.metadataserv.db.service;

import com.meta.metadataserv.domain.model.DbConf;

public interface IDbManagerService {
    /**
     * 测试连接 .
     * @param dbConf ,
     */
    void testConnection(DbConf dbConf);

    /**
     * 同步数据库元数据信息 .
     * @param dbConf .
     */
    void syncMetaData(DbConf dbConf);

    /**
     * 导出创建表sql .
     * @param tableId
     * @return
     */
    String exportTableSql(String tableId);
}
