package com.zzx.spring_security.bo;


import lombok.Data;

@Data
public class Permission {

    private int id;
    private String username;
    private int userPermission;
}
