package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.query.DbConfQueryCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description  数据操作接口
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
@Mapper
public interface DbConfDao extends BaseMapper<DbConf> {
    Page<DbConf> getDbconf(Page page, @Param("cond") DbConfQueryCond cond);
}
