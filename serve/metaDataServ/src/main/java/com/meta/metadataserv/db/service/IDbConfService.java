package com.meta.metadataserv.db.service;

import cn.hutool.db.Db;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.model.DbConf;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.query.DbConfQueryCond;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
public interface IDbConfService extends IService<DbConf> {
    /**
     * 查询数据库配置信息 .
     * @param cond
     * @return
     */
    Page<DbConf> getDbconf(DbConfQueryCond cond);

    /**
     * 新增
     * @param dbConf
     * @return
     */
    int create(DbConf dbConf);

    /**
     * 修改
     * @param dbConf
     * @return
     */
    int update(DbConf dbConf);

    /**
     * 删除
     * @param dbIds
     * @return
     */
    int delete(Set<String> dbIds);
}
