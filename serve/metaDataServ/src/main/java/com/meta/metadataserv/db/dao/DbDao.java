package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.Db;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.query.DbQueryCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description  数据操作接口
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
@Mapper
public interface DbDao extends BaseMapper<Db> {
    Page<Db> getDb(Page page, @Param("cond") DbQueryCond cond);

    List<SelectVo> getSchemaSelect();

    Integer getDbExist(@Param("dbSchema") String dbSchema);

    void updateTime(@Param("schema") String schema);
}
