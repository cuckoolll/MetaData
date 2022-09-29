package com.meta.metadataserv.sys.dao;


import com.meta.metadataserv.domain.common.SelectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {

    List<String> getRoleRelByUserId(@Param("userId") String userId);

    List<SelectVo> getRoleSelect();
}
