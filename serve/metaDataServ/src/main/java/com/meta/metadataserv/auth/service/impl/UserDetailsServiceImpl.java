package com.meta.metadataserv.auth.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.meta.metadataserv.domain.sys.User;
import com.meta.metadataserv.domain.sys.UserVo;
import com.meta.metadataserv.sys.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private HttpServletRequest request;

    @Resource
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (ObjectUtil.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        UserVo userVo = new UserVo(user);

        if (!userVo.isEnabled()) {
            throw new DisabledException("该账户已被禁用，请联系管理员!");
        } else if (!userVo.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定，请联系管理员!");
        } else if (!userVo.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期，请联系管理员!");
        } else if (!userVo.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录!");
        }

        return userVo;
    }

}
