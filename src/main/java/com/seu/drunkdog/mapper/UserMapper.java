package com.seu.drunkdog.mapper;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.entity.UserTag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Mapper
public interface UserMapper {
//    @Select("select id as id, name as name, password as password from user where id = #{id}")
//    User selectByUserId(@Param("id") int id);
    @Insert("insert into user values(null, #{name}, #{password})")
    void saveInfo(@Param("name")String name, @Param("password")String password);
    @Select("select * from user where name = #{name}")
    User getInfo(@Param("name") String name);

    @Select("select * from user_tag where user_id = #{user_id}")
    List<User>findAllById(@Param("user_id")int user_id);

    @Insert("insert into user_tag values(null, #{user_id}, #{user_tag}, #{user_weight})")
    void saveUserTag(UserTag userTag);
}
