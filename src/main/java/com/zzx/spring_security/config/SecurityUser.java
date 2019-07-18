package com.zzx.spring_security.config;

import com.zzx.spring_security.bo.Role;
import com.zzx.spring_security.bo.User;
import com.zzx.spring_security.dao.SecurityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser extends User implements UserDetails {

    @Autowired
    SecurityDao dao;

    public SecurityUser(User user) {
        if (user != null) {
            this.setUserUuid(user.getUserUuid());
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setEmail(user.getEmail());
            this.setTelephone(user.getTelephone());
            this.setLastIp(user.getLastIp());
            this.setLastTime(user.getLastTime());
            this.setRoles(user.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = this.getRoles();
        if (roles.size()>0) {
            for (Role role: roles
                 ) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
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
