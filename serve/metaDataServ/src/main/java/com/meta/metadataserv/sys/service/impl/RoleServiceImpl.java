package com.meta.metadataserv.sys.service.impl;

import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.sys.dao.RoleDao;
import com.meta.metadataserv.sys.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    private RoleDao roleDao;

    /**
     * 通过用户获取角色权限
     * @param userId
     * @return
     */
    public List<String> getRoleRelByUserId(String userId) {
        return roleDao.getRoleRelByUserId(userId);
    }

    /**
     * 查询角色下拉 .
     * @return .
     */
    public List<SelectVo> getRoleSelect() {
        return roleDao.getRoleSelect();
    }
}
