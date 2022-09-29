package com.meta.metadataserv.sys.service;

import com.meta.metadataserv.domain.common.SelectVo;

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
}
