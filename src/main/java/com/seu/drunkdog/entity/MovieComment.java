package com.seu.drunkdog.entity;


public class MovieComment {
    private int id;
    private int movieId;
    private String movieComment;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


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

    public String getMovieComment() {
        return movieComment;
    }

    public void setMovieComment(String movieComment) {
        this.movieComment = movieComment;
    }

//    public String getAll(){
//        JSONObject js=new JSONObject();
//        js.put("id",id);
//        js.put("movie_id",movie_id);
//        js.put("comment",comment);
//        return js.toString();
//    }
}
