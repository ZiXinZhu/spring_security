package com.zzx.spring_security.bo;

import lombok.Data;

@Data
public class UserBO {
    private Integer id;
    private String userUuid;   //用户UUID
    private String username;    //用户名
    private String password;    //用户密码
    private String email;       //用户邮箱
    private String telephone;   //电话号码
    private String permission;        //用户角色
    private String role;       //用户头像
    private String lastIp;     //上次登录IP
    private String lastTime;
}
