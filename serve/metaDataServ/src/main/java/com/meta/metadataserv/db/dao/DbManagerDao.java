package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.model.DbIndex;
import com.meta.metadataserv.domain.model.DbTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DbManagerDao extends BaseMapper<DbConf> {

}
