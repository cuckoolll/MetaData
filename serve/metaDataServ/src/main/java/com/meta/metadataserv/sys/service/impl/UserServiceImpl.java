package com.meta.metadataserv.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.sys.User;
import com.meta.metadataserv.domain.sys.UserVo;
import com.meta.metadataserv.security.SM2Utils;
import com.meta.metadataserv.sys.dao.UserDao;
import com.meta.metadataserv.sys.service.IUserService;
import com.meta.metadataserv.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "myUserService")
@Slf4j
public class UserServiceImpl implements IUserService {
    /**
     * 默认密码 .
     */
    private static final String DEFAULT_PASSWORD = "1234.qwer";

    @Resource
    private UserDao userDao;

    /**
     * 查看用户(根据username)
     *
     * @param username 用户名
     * @return user对象
     */
    @Override
    public User getUserByUsername(String username) {
        if (!StrUtil.isNotBlank(username)) {
            throw new RuntimeException("账号不能为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        return userDao.selectOne(queryWrapper);
    }

    /**
     * 查询所有用户信息 .
     * @param cond .
     * @return .
     */
    public Page<UserVo> getUsers(CommonQueryCond cond) {
        Page page = new Page(cond.getCurrentPage(), cond.getSize());
        return userDao.getUsers(page);
    }

    /**
     * 更新用户启用停用状态 .
     * @param userId .
     * @param status .
     */
    public void updateUserStatus(String userId, Integer status) {
        userDao.updateUserStatus(userId, status);
    }

    /**
     * 删除用户 .
     * @param userId .
     */
    public void delUser(String userId) {
        userDao.deleteById(userId);
    }

    /**
     * 创建/更新用户 .
     * @param user .
     */
    public void saveUser(UserVo user) {
        String userId = user.getUserId();

        if (StringUtils.isEmpty(userId)) {
            try {
                user.setUserId(UuidUtil.getUuid());
                user.setPassword(SM2Utils.encrypt(DEFAULT_PASSWORD));
                userDao.insertUser(user);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("创建用户异常");
            }
        }

        userDao.updateUser(user);
    }

    /**
     * 重置密码 .
     * @param userId .
     */
    public void resetPassword(String userId) throws Exception {
        userDao.resetPassword(userId, SM2Utils.encrypt(DEFAULT_PASSWORD));
    }
}
