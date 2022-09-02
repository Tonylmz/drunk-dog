package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.tool.Email;
import com.seu.drunkdog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Service
@CrossOrigin(origins = "http://localhost:8081",maxAge = 36000)
public class RegisterController {
    @Autowired
    Email emailService;
    @Autowired
    UserService userService;

    String verifyCode;
    String name;

    @RequestMapping("/register1")
    public void register1(@RequestBody User user, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        name = user.getName();
        User a = userService.LoginIn(name);
        if(a == null){
            res.put("msg", "true");
            res.put("code", 200);
            verifyCode = emailService.sendSimpleMail(name);
        }
        else{
            res.put("msg", "duplicate");
            res.put("code", 100);
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/register2")
    public void register2(@RequestBody User user, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String password = user.getPassword();
        String getVerifyCode = user.getVerifyCode();
        if(verifyCode.equals(getVerifyCode)){
            res.put("msg", "true");
            res.put("code", 200);
            userService.InsertUser(name, password);
        }
        else{
            res.put("msg", "false");
            res.put("code", 100);
        }
        response.getWriter().write(res.toString());
    }
}
