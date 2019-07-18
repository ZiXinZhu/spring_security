package com.zzx.spring_security.config;

import com.zzx.spring_security.bo.UserBO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends UserBO implements UserDetails {


    public SecurityUser(UserBO user) {
        if (user != null) {
            this.setUserUuid(user.getUserUuid());
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setEmail(user.getEmail());
            this.setTelephone(user.getTelephone());
            this.setRole(user.getRole());
            this.setPermission(user.getPermission());
            this.setLastIp(user.getLastIp());
            this.setLastTime(user.getLastTime());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String role = this.getRole();
        if (role != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            authorities.add(authority);
        }
        return authorities;
    }

    //账户是否未过期,过期无法验证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //指定用户是否解锁,锁定的用户无法进行身份验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //指示是否已过期的用户的凭据(密码),过期的凭据防止认证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否可用 ,禁用的用户不能身份验证
    @Override
    public boolean isEnabled() {
        return true;
    }
}
