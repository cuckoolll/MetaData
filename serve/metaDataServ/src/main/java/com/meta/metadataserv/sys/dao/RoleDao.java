package com.meta.metadataserv.sys.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {
    /**
     * 通过用户获取角色权限
     * @param userId
     * @return
     */
    List<String> getRoleRelByUserId(@Param("userId") String userId);
}
