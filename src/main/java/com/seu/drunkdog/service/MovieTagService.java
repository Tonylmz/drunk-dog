package com.seu.drunkdog.service;

import com.seu.drunkdog.mapper.MovieTagMapper;
import com.seu.drunkdog.entity.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class MovieTagService {
    @Autowired
    MovieTagMapper movieTagMapper;
//    @RequestMapping("/insertMovieTag")
    public void insertMovieTag(int movie_id, int movie_tag){
        movieTagMapper.saveMovieTag(movie_id, movie_tag);
    }
//    @RequestMapping("/searchMovieTag")
    public Search searchMovieTag(int movie_id){
        return movieTagMapper.getMovieTag(movie_id);
    }
}
