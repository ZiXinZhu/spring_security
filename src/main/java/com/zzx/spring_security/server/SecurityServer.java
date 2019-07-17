package com.zzx.spring_security.server;


import com.zzx.spring_security.bo.UserBO;
import com.zzx.spring_security.dao.SecurityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServer {
    @Autowired
    SecurityDao dao;

    public int add(String username,String password,String telephone,int role){
        int add=dao.add(username,password,telephone,role);
        if(add==1){
            return 1;
        }else {
            return 0;
        }

    }


}
