package com.meta.metadataserv.auth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meta.metadataserv.auth.domain.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
