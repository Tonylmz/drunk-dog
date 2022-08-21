package com.seu.drunkdog.entity;

import net.sf.json.JSONObject;

public class MovieComment {
    private int id;
    private int movie_id;
    private String comment;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAll(){
        JSONObject js=new JSONObject();
        js.put("id",id);
        js.put("movie_id",movie_id);
        js.put("comment",comment);
        return js.toString();
    }
}
