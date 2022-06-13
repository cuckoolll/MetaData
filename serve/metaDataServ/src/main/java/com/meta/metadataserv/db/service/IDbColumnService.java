package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;

public interface IDbColumnService extends IService<DbColumn>  {
    /**
     * 通过元数据拉取字段，进行保存 .
     */
    void syncColumn(DbConf dbConf);
}
