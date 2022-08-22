package com.seu.drunkdog.service;

import com.seu.drunkdog.entity.Movie;
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
    public Movie searchMovie(int id){
        return movieMapper.getMovie(id);
    }
    public Movie searchTopMovie(int id){
        return movieMapper.getTopMovie(id);
    }
    public List<Movie> searchAllMovie(){
        return movieMapper.findAll();
    }
    public void insertMovieComment(int movie_id, String movie_comment){
        movieMapper.saveMovieComment(movie_id, movie_comment);
    }
    public List<String> searchMovieCommentById(int movie_id){
        return movieMapper.getMovieComment(movie_id);
    }
    public List<Movie> searchMovieByNameOrDirectorOrActor(String name){ return movieMapper.getMovieByNameOrDirectorOrActor(name); }
}
