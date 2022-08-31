package com.seu.drunkdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TagCloudMapper {
    @Select("select tags from tag_cloud where movie_id = #{movie_id}")
    String getTagCloud(@Param("movie_id") int movie_id);
}
