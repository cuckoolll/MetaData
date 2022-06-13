package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.model.DbIndex;

public interface IDbIndexService extends IService<DbIndex> {
    /**
     * 通过元数据拉去索引，进行保存 .
     * @param dbConf
     */
    void syncIndex(DbConf dbConf);
}
