package com.seu.drunkdog.mapper;

import com.seu.drunkdog.entity.Movie;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MovieMapper {
    @Select("select * from movie_top")
    List<Movie> findAll();
    @Insert("insert into movie values(null, #{movie_id},#{name},#{actor},#{cover},#{director},#{score},#{category},#{mins},#{regions},#{release_period}")
    void saveMovie(Movie movie);
    @Select("select * from movie where movie_id=#{movie_id}")
    Movie getMovie(@Param("movie_id") int movie_id);
    @Select("select * from movie_august where movie_id=#{movie_id}")
    Movie getAugustMovie(@Param("movie_id") int movie_id);

    @Select("select * from movie_top where movie_id = #{movie_id}")
    Movie getTopMovie(@Param("movie_id") int movie_id);

    @Insert("insert into movie_comment values(#{movie_id}, #{movie_comment},null,null)")
    void saveMovieComment(@Param("movie_id") int movie_id, @Param("movie_comment") String movie_comment);
    @Update("update movie_comment set score = #{score} where movie_id = #{movie_id}")
    void saveMovieScore(@Param("movie_id") int movie_id, @Param("score") int score);
    @Select("select movie_comment from movie_comment where movie_id = #{movie_id}")
    List<String> getMovieComment(@Param("movie_id") int movie_id);

    @Select("select * from movie where name like concat('%',#{name},'%') ")
    List<Movie> getMovieByName(@Param("name") String name);
    @Select("select * from movie where director like concat('%',#{name},'%') or actor like concat('%',#{name},'%')")
    List<Movie> getMovieByDirectorOrActor(@Param("name") String name);
    @Select("select * from movie_august")
    List<Movie> movieAugust();
    @Select("select * from movie_top where country like '%美国%' order by score desc limit 0,5")
    List<Movie> movieAmerica();
    @Select("select count(*) from movie_top where country like '%美国%'")
    int numberOfAmericanMovie();
    @Select("select * from movie_top where country like '%中国大陆%' or '%中国香港%' or '%中国台湾%' order by score desc limit 0,5")
    List<Movie> movieChina();
    @Select("select count(*) from movie_top where country like '%中国大陆%' or '%中国香港%' or '%中国台湾%'")
    int numberOfChineseMovie();
    @Select("select * from movie_top where country like '%英国%' order by score desc limit 0,5")
    List<Movie> movieEngland();
    @Select("select count(*) from movie_top where country like '%英国%'")
    int numberOfEnglishMovie();
    @Select("select * from movie_top where country like '%法国%' order by score desc limit 0,5")
    List<Movie> movieFrance();
    @Select("select count(*) from movie_top where country like '%法国%'")
    int numberOfFrenchMovie();
    @Select("select * from movie_top where country like '%德国%' order by score desc limit 0,5")
    List<Movie> movieGermany();
    @Select("select count(*) from movie_top where country like '%德国%'")
    int numberOfGermanMovie();
}
