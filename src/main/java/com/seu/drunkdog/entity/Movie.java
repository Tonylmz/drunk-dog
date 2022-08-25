package com.seu.drunkdog.entity;

//import net.sf.json.JSONObject;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import javax.persistence.Id;


public class Movie {
    private int id;
    private int movie_id;
    private String year;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String name;
    private String director;
    private String actor;

    private String category;
    private double score;
    private String cover;
    private String release_period;

    private String mins;

    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPicture_link() {
        return picture_link;
    }

    public void setPicture_link(String picture_link) {
        this.picture_link = picture_link;
    }

    private String picture_link;
    private String regions;
    private String storyline;

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRelease_period() {
        return release_period;
    }

    public void setRelease_period(String release_period) {
        this.release_period = release_period;
    }

//    public String getAll(){
//        JSONObject js=new JSONObject();
//        js.put("id",id);
//        js.put("name",name);
//        js.put("introduction",introduction);
//        js.put("director",director);js.put("actor",actor);
//        js.put("category",category);
//        js.put("scorer",score);
//        js.put("cover", cover);
//        js.put("release_period",release_period);
//        js.put("mins", mins);
//        js.put("regions", regions);
//        return js.toString();
//    }
}
