package com.seu.drunkdog.entity;

import javax.persistence.Id;


public class MovieComment {
    private int id;
    private int movie_id;
    private String movie_comment;

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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

    public String getMovie_comment() {
        return movie_comment;
    }

    public void setMovie_comment(String movie_comment) {
        this.movie_comment = movie_comment;
    }

//    public String getAll(){
//        JSONObject js=new JSONObject();
//        js.put("id",id);
//        js.put("movie_id",movie_id);
//        js.put("comment",comment);
//        return js.toString();
//    }
}
