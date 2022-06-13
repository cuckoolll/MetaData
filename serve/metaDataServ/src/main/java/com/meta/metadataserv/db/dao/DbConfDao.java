package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.model.DbConf;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
@Mapper
public interface DbConfDao extends BaseMapper<DbConf> {
    Page<DbConf> getDbconf(Page page);
}
