package com.seu.drunkdog.entity;


public class Search {
//    private int id;
    private int userId;
//    private int movieTag;
    private String name;

    private int pageNo;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //    private double movie_weight;

//    @Id
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

//    public int getMovieTag() {
//        return movieTag;
//    }
//
//    public void setMovieTag(int movieTag) {
//        this.movieTag = movieTag;
//    }

//    public double getMovie_weight() {
//        return movie_weight;
//    }
//
//    public void setMovie_weight(double movie_weight) {
//        this.movie_weight = movie_weight;
//    }
}
