package com.seu.drunkdog.mapper;

import com.seu.drunkdog.entity.Search;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface MovieTagMapper {
    @Insert("insert into movie_tag values(null, #{movie_id}, #{movie_tag})")
    void saveMovieTag(@Param("movie_id")int movie_id, @Param("movie_tag") int movie_tag);
    @Select("select * from movie_tag where movie_id = #{movie_id}")
    Search getMovieTag(@Param("movie_id") int movie_id);
}
