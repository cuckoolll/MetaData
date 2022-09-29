package com.meta.metadataserv.domain.sys;

import com.meta.metadataserv.security.SM2Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserVo implements UserDetails {

    private String id;

    private String userId;

    private String username;

    private String password;

    private String nickName;

    private String email;

    private String phone;

    private String datastatusid;

    private String roleId;

    private String roleName;

    private Boolean enabled;

    private String clientId;

    private String operationGroupId;

    private String operationCompanyId;

    private Collection<SimpleGrantedAuthority> authorities;

    public UserVo(User user) throws IOException {
        this.setId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setPassword(new BCryptPasswordEncoder().encode(SM2Utils.decrypt(user.getPassword())));
        this.setNickName(user.getNickName());
        this.setEmail(user.getEmail());
        this.setEnabled(Integer.valueOf(1).equals(user.getDatastatusid()));
        this.setClientId(user.getClientId());
        if (user.getRoleId() != null) {
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(String.valueOf(user.getRoleId())));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
