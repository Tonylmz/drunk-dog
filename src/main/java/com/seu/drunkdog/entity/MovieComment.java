package com.seu.drunkdog.entity;

public class MovieComment {
    private int id;
    private int movie_id;
    private String comment;
    private double score;

    public int getId() {
        return id;
    }

    public MovieComment(int id, int movie_id, String comment, double score) {
        this.id = id;
        this.movie_id = movie_id;
        this.comment = comment;
        this.score = score;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
