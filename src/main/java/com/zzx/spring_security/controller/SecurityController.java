package com.zzx.spring_security.controller;

import com.zzx.spring_security.bo.User;
import com.zzx.spring_security.server.SecurityServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SecurityController {

    @Autowired
    SecurityServer server;


    @RequestMapping(value = "/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/")
    public void root(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8080/index.html");
    }

    @RequestMapping("/index")
    public void index(HttpServletResponse response) throws IOException{
        response.sendRedirect("http://localhost:8080/index.html");
    }

    @RequestMapping("/register")
    @Transactional
    public String add(String username,String password,String telephone,String  role){

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
            password=encoder.encode(password);
            int adduser=server.add(username,password,telephone);
            int addrolr=server.addrole(username,role);
            int addPermission=server.addPermission(username,1);
            if(adduser==1&&addrolr==1&&addPermission==1){
                return "添加成功!";
            }
            return "添加失敗!";


    }

    public User getUser() { //为了session从获取用户信息,可以配置如下
        User user = new User();
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) user = (User) auth.getPrincipal();
        return user;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
