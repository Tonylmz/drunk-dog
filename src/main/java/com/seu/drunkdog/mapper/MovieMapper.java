package com.seu.drunkdog.mapper;

import com.seu.drunkdog.entity.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface MovieMapper {
    @Select("select * from movie_top")
    List<Movie> findAll();
    @Insert("insert into movie values(null, #{name},#{introduction},#{director},#{actor},#{category},#{score},#{picture_link},#{release_period}")
    void saveMovie(Movie movie);
    @Select("select * from movie where id=#{id}")
    Movie getMovie(@Param("id") int id);

}
