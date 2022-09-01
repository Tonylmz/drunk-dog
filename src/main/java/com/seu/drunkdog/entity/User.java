package com.seu.drunkdog.entity;

import javax.persistence.Id;
import java.util.List;


public class User {
    private int id;
    private String name;
    private String password;

    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public int getIfFirstLogin() {
        return ifFirstLogin;
    }

    public void setIfFirstLogin(int ifFirstLogin) {
        this.ifFirstLogin = ifFirstLogin;
    }

    private int ifFirstLogin;

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

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
            weight_vector[weightList.get(i).getUserTag()] = weightList.get(i).getUserWeight();
        }
        return weight_vector;
    }
    public String toString()
    {
        return "success "+ (weightList.size()) ;
    }
}
