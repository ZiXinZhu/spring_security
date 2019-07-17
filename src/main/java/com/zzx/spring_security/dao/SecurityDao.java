package com.zzx.spring_security.dao;


import com.zzx.spring_security.bo.UserBO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SecurityDao {


    @Insert("insert into user (username,password,telephone,role) values (#{username},#{password},#{telephone},#{role})")
     int add(@Param("username")String  username,
             @Param("password")String password,
             @Param("telephone")String  telephone,
             @Param("role")int  role);

    @Select("SELECT * from user where username=#{username}")
    UserBO getOne(@Param("username")String  username);

}
