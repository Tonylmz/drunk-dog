package com.seu.drunkdog.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;


public class User {
    private int id;
    private String name;
    private String password;

    private List<UserTag> weightList;

    @Id
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
    public Integer[] feature_vector(){
        Integer[] weight_vector = new Integer[100];
        for (int i=0; i < 100; i++){
            weight_vector[i] = 0;
        }
        for (int i=0; i < weightList.size(); i++){
            weight_vector[weightList.get(i).getUser_tag()] = weightList.get(i).getUser_weight();
        }
        return weight_vector;
    }
    public String toString()
    {
        return "success "+ (weightList.size()) ;
    }
}
