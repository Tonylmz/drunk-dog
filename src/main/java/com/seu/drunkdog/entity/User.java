package com.seu.drunkdog.entity;

public class User {
    private int id;
    private String name;
    private String password;

    public User(int id, String user_name, String password) {
        this.id = id;
        this.name = user_name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
