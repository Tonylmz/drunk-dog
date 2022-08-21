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

    @Select("select * from movie_top where id = #{id}")
    Movie getTopMovie(@Param("id") int id);

    @Insert("insert into movie_comment values(null, #{movie_id}, #{movie_comment})")
    void saveMovieComment(@Param("movie_id") int movie_id, @Param("movie_comment") String movie_comment);

    @Select("select movie_comment from movie_comment where movie_id = #{movie_id}")
    List<String> getMovieComment(@Param("movie_id") int movie_id);
}
