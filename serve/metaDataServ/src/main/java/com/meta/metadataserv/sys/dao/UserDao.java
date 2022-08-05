package com.meta.metadataserv.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meta.metadataserv.domain.sys.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
