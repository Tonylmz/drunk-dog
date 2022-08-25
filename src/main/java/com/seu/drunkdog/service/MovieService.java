package com.seu.drunkdog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.entity.MovieComment;
import com.seu.drunkdog.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Service
public class MovieService {
    @Autowired
    MovieMapper movieMapper;
    public void insertMovie(Movie movie){
        movieMapper.saveMovie(movie);
    }
    public Movie searchMovie(int movie_id){
        return movieMapper.getMovie(movie_id);
    }
    public Movie searchAugustMovie(int movie_id){
        return movieMapper.getAugustMovie(movie_id);
    }
    public Movie searchTopMovie(int id){
        return movieMapper.getTopMovie(id);
    }
    public IPage<Movie> searchAllTopMovie(Page<Movie> page){
        return movieMapper.findAllTopMovieByPage(page);
    }
    public void insertMovieComment(int movie_id, String movie_comment){
        movieMapper.saveMovieComment(movie_id, movie_comment);
    }
//    public void insertMovieScore(int movie_id, int score){
//        movieMapper.saveMovieScore(movie_id, score);
//    }
    public void addMovieLike(int id){
        movieMapper.saveClickLike(id);
    }
    public List<MovieComment> searchMovieCommentById(int movie_id){
        return movieMapper.getMovieComment(movie_id);
    }

    public IPage<MovieComment> searchMovieCommentByIdAndPage(int movie_id, Page<MovieComment> page){
        return movieMapper.getMovieCommentByPage(movie_id, page);
    }
    public List<Movie> searchMovieByName(String name){ return movieMapper.getMovieByName(name); }
    public List<Movie> searchMovieByDirectorOrActor(String name){ return movieMapper.getMovieByDirectorOrActor(name); }
    public List<Movie> getAllAugustMovie(){ return movieMapper.movieAugust(); }
    public int numOfAmericanMovie(){ return movieMapper.numberOfAmericanMovie();}
    public List<Movie> getMovieAmerica(){ return movieMapper.movieAmerica(); }
    public int numOfChineseMovie(){ return movieMapper.numberOfChineseMovie();}
    public List<Movie> getMovieChina(){ return movieMapper.movieChina(); }
    public int numOfEnglishMovie(){ return movieMapper.numberOfEnglishMovie();}
    public List<Movie> getMovieEngland(){ return movieMapper.movieEngland(); }
    public int numOfFrenchMovie(){ return movieMapper.numberOfFrenchMovie();}
    public List<Movie> getMovieFrance(){ return movieMapper.movieFrance(); }
    public int numOfGermanMovie(){ return movieMapper.numberOfGermanMovie();}
    public List<Movie> getMovieGermany(){ return movieMapper.movieGermany(); }

    public IPage<Movie> getMovieByPage(Page<Movie> page){ return movieMapper.selectMovieByPage(page); }
}
