package com.zzx.spring_security.bo;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;
    private String userUuid;   //用户UUID
    private String username;    //用户名
    private String password;    //用户密码
    private String email;       //用户邮箱
    private String telephone;   //电话号码
    private String lastIp;     //上次登录IP
    private String lastTime;
    private List<Role> roles;
}
