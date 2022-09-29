package com.meta.metadataserv.sys.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.sys.User;
import com.meta.metadataserv.domain.sys.UserVo;

public interface IUserService {

    /**
     * 查看用户(根据username)
     *
     * @param username 用户名
     * @return user对象
     */
    User getUserByUsername(String username);

    /**
     * 查询所有用户信息 .
     * @param cond .
     * @return .
     */
    Page<UserVo> getUsers(CommonQueryCond cond);

    /**
     * 更新用户启用停用状态 .
     * @param userId .
     * @param status .
     */
    void updateUserStatus(String userId, Integer status);

    /**
     * 删除用户 .
     * @param userId .
     */
    void delUser(String userId);

    /**
     * 创建/更新用户 .
     * @param user .
     */
    void saveUser(UserVo user);

    /**
     * 重置密码 .
     * @param userId .
     */
    void resetPassword(String userId) throws Exception;
}
