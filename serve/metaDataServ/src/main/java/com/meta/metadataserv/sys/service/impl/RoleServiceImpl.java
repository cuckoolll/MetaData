package com.meta.metadataserv.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.sys.RoleVo;
import com.meta.metadataserv.security.SM2Utils;
import com.meta.metadataserv.sys.dao.RoleDao;
import com.meta.metadataserv.sys.service.IRoleService;
import com.meta.metadataserv.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
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

    /**
     * 查询角色 .
     * @param cond .
     * @return .
     */
    public Page<RoleVo> getRoles(CommonQueryCond cond) {
        Page page = new Page(cond.getCurrentPage(), cond.getSize());
        return roleDao.getRoles(page, cond);
    }

    /**
     * 更新角色启动状态 .
     * @param roleId .
     * @param status .
     */
    public void updateRoleStatus(String roleId, Integer status) {
        roleDao.updateRoleStatus(roleId, status);
    }

    /**
     * 删除角色 .
     * @param roleId .
     */
    public void delRole(String roleId) {
        roleDao.delRole(roleId);
    }

    /**
     * 创建/更新角色
     * @param role
     */
    public void saveRole(RoleVo role) {
        String roleId = role.getRoleId();

        if (StringUtils.isEmpty(roleId)) {
            try {
                role.setRoleId(UuidUtil.getUuid());
                roleDao.insertRole(role);
                return;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("创建角色异常");
            }
        }
        roleDao.updateRole(role);
    }
}
