package com.meta.metadataserv.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.domain.sys.RoleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IRoleService {
    /**
     * 通过用户获取角色权限
     * @param userId
     * @return
     */
    List<String> getRoleRelByUserId(String userId);

    /**
     * 查询角色下拉 .
     * @return .
     */
    List<SelectVo> getRoleSelect();

    /**
     * 查询角色 .
     * @param cond .
     * @return .
     */
    Page<RoleVo> getRoles(CommonQueryCond cond);

    /**
     * 更新角色启动状态 .
     * @param roleId .
     * @param status .
     */
    void updateRoleStatus(String roleId, Integer status);

    /**
     * 删除角色 .
     * @param roleId .
     */
    void delRole(String roleId);

    /**
     * 创建/更新角色
     * @param role
     */
    void saveRole(RoleVo role);
}
