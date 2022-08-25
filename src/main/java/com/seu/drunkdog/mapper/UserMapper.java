package com.seu.drunkdog.mapper;

import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.entity.UserTag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper{
//    @Select("select id as id, name as name, password as password from user where id = #{id}")
//    User selectByUserId(@Param("id") int id);
    @Insert("insert into user values(null, #{name}, #{password})")
    void saveInfo(@Param("name")String name, @Param("password")String password);
    @Select("select * from user where name = #{name}")
    User getInfo(@Param("name") String name);

    @Select("select * from user where id = #{id}")
    User getInfoById(@Param("id") int id);
    @Select("select * from user_tag where user_id = #{user_id}")
    List<UserTag> findAllById(@Param("user_id")int user_id);
    @Select("select * from user_tag where user_id = #{user_id} and user_tag = #{user_tag}")
    UserTag findByIdAndTag(@Param("user_id") int user_id, @Param("user_tag") int user_tag);
    @Update("update user_tag set user_weight = greatest(0, user_weight + #{user_weight}) where user_id = #{user_id} and user_tag = #{user_tag}")
    void updateUserWeightByIdAndTag(@Param("user_id") int user_id, @Param("user_tag") int user_tag, @Param("user_weight") double user_weight);

    @Insert("insert into user_tag values(null, #{user_id}, #{user_tag}, #{user_weight})")
    void saveUserTag(@Param("user_id") int user_id, @Param("user_tag") int user_tag, @Param("user_weight") double user_weight);

    @Select("select id from tag where category = #{category}")
    int getIdByTag(@Param("category")String category);

//    @Update("update user_tag set user_weight = user_weight + 1 where user_id = #{user_id}")
//    void updateUserWeight(@Param("user_id") int user_id);

    @Select("select * from movie where category like concat('%|',#{tag},'|%')")
    List<Movie> getAllMovieByTag(@Param("tag") int tag);

    @Select("select * from user_tag where user_id = #{id}")
    User sel(@Param("id") int id);
}
