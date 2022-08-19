package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@Service
public class LoginController {
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public String show(){return "login";}

    @RequestMapping("/register")
    public String register(String name, String password){
        userService.InsertUser(name, password);
        return "success";
    }
    @RequestMapping("/loginIn")
    public String login(String name, String password){
        User user = userService.LoginIn(name, password);
        log.info("name:{}",name);
        log.info("password:{}",password);
        if(user!=null){
            return "success";
        }
        else {
            return "error";
        }
    }
}
