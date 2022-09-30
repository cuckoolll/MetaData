package com.meta.metadataserv.sys.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.sys.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {

    List<String> getRoleRelByUserId(@Param("userId") String userId);

    List<SelectVo> getRoleSelect();

    Page<RoleVo> getRoles(Page page, @Param("cond") CommonQueryCond cond);

    void updateRoleStatus(@Param("roleId") String roleId, @Param("status") Integer status);

    void delRole(@Param("roleId") String roleId);

    void insertRole(@Param("role") RoleVo role);

    void updateRole(@Param("role") RoleVo role);
}
