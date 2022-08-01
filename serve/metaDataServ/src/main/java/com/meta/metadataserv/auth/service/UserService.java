package com.meta.metadataserv.auth.service;


import com.meta.metadataserv.auth.domain.model.User;

public interface UserService {

    /**
     * 查看用户(根据username)
     *
     * @param username 用户名
     * @return user对象
     */
    User getUserByUsername(String username);
}
