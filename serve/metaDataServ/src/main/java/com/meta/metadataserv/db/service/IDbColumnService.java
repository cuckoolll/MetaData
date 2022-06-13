package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IDbColumnService extends IService<DbColumn>  {
    /**
     * 通过元数据拉取字段，进行保存 .
     */
    void syncColumn(DbConf dbConf);

    /**
     * 查询表字段
     * @param tableName
     * @return
     */
    List<DbColumn> getDbColumn(String tableName);
}
