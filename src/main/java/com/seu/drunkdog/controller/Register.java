package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.mapper.UserMapper;
import com.seu.drunkdog.service.EmailService;
import com.seu.drunkdog.service.UserService;
import com.seu.drunkdog.tool.EmailNotofication;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping("/user")
@Service
public class Register {
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;
    @RequestMapping("/register")
    public String register(String name, String password){
        userService.InsertUser(name, password);
        return emailService.sendSimpleMail(name);
    }

    private  static HashMap<String,String> codemap=new HashMap<>();
    @RequestMapping("/register2")
    public void register2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        User a = new User();
        a.setName(name);
        a.setPassword(password);
        String verifyCode = emailService.sendSimpleMail(name);
        String code = request.getParameter("code");
        if(verifyCode!=code){
            res.put("msg", "false");
        }
        else{
            userService.InsertUser(name, password);
            Cookie c=new Cookie("username",name);
            c.setMaxAge(10800);
            c.setPath("/");
            Cookie d=new Cookie("islogin","true");
            d.setMaxAge(10800);
            d.setPath("/");
            response.addCookie(c);
            response.addCookie(d);
        }
        response.getWriter().write(res.toString());
    }
}
