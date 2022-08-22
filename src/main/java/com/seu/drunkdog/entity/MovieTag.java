package com.seu.drunkdog.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;


public class MovieTag {
    private int id;
    private int movie_id;
    private int movie_tag;
//    private double movie_weight;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getMovie_tag() {
        return movie_tag;
    }

    public void setMovie_tag(int movie_tag) {
        this.movie_tag = movie_tag;
    }

//    public double getMovie_weight() {
//        return movie_weight;
//    }
//
//    public void setMovie_weight(double movie_weight) {
//        this.movie_weight = movie_weight;
//    }
}
