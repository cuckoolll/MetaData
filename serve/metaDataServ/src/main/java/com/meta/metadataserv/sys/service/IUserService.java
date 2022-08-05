package com.meta.metadataserv.sys.service;


import com.meta.metadataserv.domain.sys.User;

public interface IUserService {

    /**
     * 查看用户(根据username)
     *
     * @param username 用户名
     * @return user对象
     */
    User getUserByUsername(String username);
}
