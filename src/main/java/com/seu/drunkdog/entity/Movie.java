package com.seu.drunkdog.entity;

//import net.sf.json.JSONObject;


public class Movie {
    private int id;
    private int movieId;
    private String year;
    private String name;
    private String director;
    private String actor;

    private String category;
    private double score;
    private String cover;
    private String releasePeriod;
    private String mins;
    private String country;
    private String pictureLink;
    private String regions;
    private String storyline;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }



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

    public String getReleasePeriod() {
        return releasePeriod;
    }

    public void setReleasePeriod(String releasePeriod) {
        this.releasePeriod = releasePeriod;
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
