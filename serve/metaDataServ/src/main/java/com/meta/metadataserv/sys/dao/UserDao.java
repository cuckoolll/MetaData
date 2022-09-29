package com.meta.metadataserv.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.sys.User;
import com.meta.metadataserv.domain.sys.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<User> {
    Page<UserVo> getUsers(Page page);

    void updateUserStatus(@Param("userId") String userId, @Param("status") Integer status);

    void updateUser(@Param("user") UserVo user);

    void insertUser(@Param("user") UserVo user);

    void resetPassword(@Param("userId") String userId, @Param("password") String password);
}
