package com.seu.drunkdog.entity;

public class UserTag {
    private int id;
    private int user_id;
    private int user_tag;
    private double user_weight;

    public UserTag(int id, int user_id, int user_tag, double user_weight) {
        this.id = id;
        this.user_id = user_id;
        this.user_tag = user_tag;
        this.user_weight = user_weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_tag() {
        return user_tag;
    }

    public void setUser_tag(int user_tag) {
        this.user_tag = user_tag;
    }

    public double getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(double user_weight) {
        this.user_weight = user_weight;
    }
}
