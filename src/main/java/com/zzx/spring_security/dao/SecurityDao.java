package com.zzx.spring_security.dao;


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

    @Insert("INSERT INTO permission (username,user_permission) values (#{username},#{userPermission})")
    int addrPermission(@Param("username")String  username,
                @Param("userPermission")int  userPermission);

    @Select("SELECT * from user where username=#{username}")
    User getOne(@Param("username")String  username);

    @Select("SELECT * FROM role where username=#{username}")
    List<Role>  getrole(@Param("username")String  username);

}
