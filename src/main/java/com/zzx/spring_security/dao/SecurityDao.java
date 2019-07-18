package com.zzx.spring_security.dao;


import com.zzx.spring_security.bo.Permission;
import com.zzx.spring_security.bo.Role;
import com.zzx.spring_security.bo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SecurityDao {


    @Insert("insert into user (username,password,telephone) values (#{username},#{password},#{telephone})")
     int add(@Param("username")String  username,
             @Param("password")String password,
             @Param("telephone")String  telephone);

    @Insert("INSERT INTO role (username,name) values (#{username},#{name})")
    int addrole(@Param("username")String  username,
                @Param("name")String  name);

    @Insert("INSERT INTO permission (name,role,url) values (#{name},#{role},#{url})")
    int addrPermission(@Param("name")String  name,
                       @Param("role")String  role,
                       @Param("url")String  url);

    @Select("SELECT * from user where username=#{username}")
    User getOne(@Param("username")String  username);

    @Select("SELECT * FROM role where username=#{username}")
    List<Role>  getrole(@Param("username")String  username);

    @Select("Select * from permission")
    List<Permission> allPermission();

    @Select("Select role from permission where url=#{url}")
    List<String>  allrole(@Param("url")String  url);

    @Select("SELECT count(username) FROM user where username=#{username}")
    int getusername(@Param("username")String  username);

}
