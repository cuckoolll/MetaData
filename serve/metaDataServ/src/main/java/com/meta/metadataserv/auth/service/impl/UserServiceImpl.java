package com.meta.metadataserv.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meta.metadataserv.auth.dao.UserDao;
import com.meta.metadataserv.auth.domain.model.User;
import com.meta.metadataserv.auth.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "myUserService")
public class UserServiceImpl implements UserService {

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

}
