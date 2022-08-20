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
    public List<Movie> getAllMovie(){
        return movieMapper.findAll();
    }
}
