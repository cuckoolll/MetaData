package com.meta.metadataserv.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meta.metadataserv.domain.sys.User;
import com.meta.metadataserv.sys.dao.UserDao;
import com.meta.metadataserv.sys.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "myUserService")
public class UserServiceImpl implements IUserService {

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
