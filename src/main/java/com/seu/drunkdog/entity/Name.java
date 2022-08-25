package com.seu.drunkdog.entity;

import javax.persistence.Id;


public class Name {
    private int userId;

    private String name;

    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
