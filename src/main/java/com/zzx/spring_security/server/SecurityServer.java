package com.zzx.spring_security.server;


import com.zzx.spring_security.dao.SecurityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServer {
    @Autowired
    SecurityDao dao;

    public int add(String username,String password,String telephone){
        int add=dao.add(username,password,telephone);
        if(add==1){
            return 1;
        }else {
            return 0;
        }

    }

    public int addrole(String username,String name){
        int addrole=dao.addrole(username,name);
        if(addrole==1) return 1;
        else return 0;

    }

    public int addPermission(String name,String role,String url){
        int addPermission=dao.addrPermission(name,role,url);
        if(addPermission==1) return 1;
        else return 0;

    }


}
