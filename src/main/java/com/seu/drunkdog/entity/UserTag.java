package com.seu.drunkdog.entity;

import javax.persistence.Id;


public class UserTag {
    private int id;
    private int userId;
    private int userTag;
    private int userWeight;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserTag() {
        return userTag;
    }

    public void setUserTag(int userTag) {
        this.userTag = userTag;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

}
