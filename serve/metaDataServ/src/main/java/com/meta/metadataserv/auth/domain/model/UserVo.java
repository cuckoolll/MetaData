package com.meta.metadataserv.auth.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserVo implements UserDetails {

    private String id;

    private String username;

    private String password;

    private String nickName;

    private String email;

    private Boolean enabled;

    private String clientId;

    private String operationGroupId;

    private String operationCompanyId;

    private Collection<SimpleGrantedAuthority> authorities;

    public UserVo(User user) {
        this.setId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        this.setNickName(user.getNickName());
        this.setEmail(user.getEmail());
        this.setEnabled(Integer.valueOf(1).equals(user.getDatastatusid()));
        this.setClientId(user.getClientId());
        if (user.getRoles() != null) {
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(String.valueOf(user.getRoles())));
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
