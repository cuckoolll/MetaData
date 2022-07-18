package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.model.DbIndex;

import java.util.List;

public interface IDbIndexService extends IService<DbIndex> {
    /**
     * 通过元数据拉去索引，进行保存 .
     * @param dbConf
     */
    void syncIndex(DbConf dbConf);

    /**
     * 查询索引 .
     * @param tableName
     * @return
     */
    List<DbIndex> getDbIndex(String tableName, String schema);
}
