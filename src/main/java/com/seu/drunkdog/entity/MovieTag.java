package com.seu.drunkdog.entity;

import javax.persistence.Id;


public class MovieTag {
    private int id;
    private int movieId;
    private int movieTag;
//    private double movie_weight;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieTag() {
        return movieTag;
    }

    public void setMovieTag(int movieTag) {
        this.movieTag = movieTag;
    }

//    public double getMovie_weight() {
//        return movie_weight;
//    }
//
//    public void setMovie_weight(double movie_weight) {
//        this.movie_weight = movie_weight;
//    }
}
