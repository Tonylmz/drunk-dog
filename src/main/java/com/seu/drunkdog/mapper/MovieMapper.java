package com.seu.drunkdog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seu.drunkdog.entity.HmmResult;
import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.entity.MovieComment;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MovieMapper{
    @Select("select * from movie_top")
    IPage<Movie> findAllTopMovieByPage(Page<Movie> page);
    @Insert("insert into movie values(null, #{movie_id},#{name},#{actor},#{cover},#{director},#{score},#{category},#{mins},#{regions},#{release_period}")
    void saveMovie(Movie movie);
    @Select("select * from movie where movie_id=#{movie_id}")
    Movie getMovie(@Param("movie_id") int movie_id);
    @Select("select * from movie_august where movie_id=#{movie_id}")
    Movie getAugustMovie(@Param("movie_id") int movie_id);

    @Select("select * from movie_top where movie_id = #{movie_id}")
    Movie getTopMovie(@Param("movie_id") int movie_id);

    @Insert("insert into movie_comment values(#{movie_id}, #{movie_comment},#{score},null)")
    void saveMovieComment(@Param("movie_id") int movie_id, @Param("movie_comment") String movie_comment, @Param("score") int score);
    @Update("update movie_comment set score = score + 1 where id = #{id}")
    void saveClickLike(@Param("id") int id);
    @Select("select * from movie_comment where movie_id = #{movie_id}")
    List<MovieComment> getMovieComment(@Param("movie_id") int movie_id);
    @Select("select * from movie_comment where movie_id = #{movie_id} order by score desc")
    IPage<MovieComment> getMovieCommentByPage(@Param("movie_id") int movie_id, Page<MovieComment> page);

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
    @Select("select * from movie_top where country like '%日本%' or country like '%韩国%' order by score desc limit 0,5")
    List<Movie> movieJapanOrKorea();
    @Select("select count(*) from movie_top where country like '%韩国%' or country like '%日本%'")
    int numberOfGermanMovie();
    @Select("select * from movie")
    IPage<Movie> selectMovieByPage(Page<Movie> page);

    @Select("select * from movie where category like concat('%|', #{category}, '|%')")
    IPage<Movie> selectMovieByCategoryAndPage(Page<Movie> page, @Param("category") int category);
    @Delete("delete from python_movie")
    void deleteFromMoviePython();

    @Select("select movie_id from python_movie")
    List<Integer> getFromMoviePython();

    @Select("select * from hmm_result")
    List<HmmResult> getResult();

    @Delete("delete from hmm_result")
    void deleteResult();

    @Insert("insert into hmm_result values(#{result})")
    void saveResult(String result);

    @Select("select max(score) from movie_comment where movie_id = #{movie_id}")
    int maxScore(@Param("movie_id") int movie_id);

    @Select("select * from answer_robot")
    String answer();
}
