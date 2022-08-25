package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.service.EmailService;
import com.seu.drunkdog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
//@RequestMapping("/user")
@Service
@CrossOrigin(origins = "http://localhost:8080",maxAge = 36000)
public class RegisterController {
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;

    String verifyCode;
    String name;

//    @RequestMapping("/register")
//    public String register(String name, String password){
//        userService.InsertUser(name, password);
//        return emailService.sendSimpleMail(name);
//    }
//    private  static HashMap<String,String> codemap=new HashMap<>();
    @RequestMapping("/register1")
    public void register1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        name = request.getParameter("name");
//        String password = request.getParameter("password");
//        User a = new User();
//        a.setName(name);
//        a.setPassword(password);
//        hs.setAttribute("name",name);

        User a = userService.LoginIn(name);
        if(a == null){
            res.put("msg", "true");
            res.put("code", 0);
            verifyCode = emailService.sendSimpleMail(name);
            Cookie c = new Cookie("verifyCode", verifyCode);
            c.setMaxAge(10800);
            c.setPath("/");
            response.addCookie(c);
            Cookie d=new Cookie("register1","true");
            d.setMaxAge(10800);
            d.setPath("/");

            response.addCookie(c);
            response.addCookie(d);
        }
        else{
            res.put("msg", "duplicate");
            res.put("code", 1);
            Cookie c = new Cookie("register1", "duplicate");
            c.setMaxAge(10800);
            c.setPath("/");
            response.addCookie(c);
        }
//        res.put("verifycode", verifyCode);
//        Cookie c = new Cookie("verifyCode", verifyCode);
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(c);
//        String code = request.getParameter("code");
//        if(verifyCode!=code){
//            res.put("msg", "false");
//        }
//        else{
//            userService.InsertUser(name, password);
//            Cookie c=new Cookie("name",name);
//            c.setMaxAge(10800);
//            c.setPath("/");
//            Cookie d=new Cookie("register","true");
//            d.setMaxAge(10800);
//            d.setPath("/");
//            response.addCookie(c);
//            response.addCookie(d);
//        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/register2")
    public void register2(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String password = request.getParameter("password");
        String getVerifyCode = request.getParameter("verifyCode");
        if(verifyCode.equals(getVerifyCode)){
            res.put("msg", "true");
            res.put("code", 0);
            userService.InsertUser(name, password);
            Cookie c = new Cookie("register2","true");
            c.setMaxAge(10800);
            c.setPath("/");
            response.addCookie(c);
        }
        else{
            res.put("msg", "false");
            res.put("code", 1);
            Cookie c = new Cookie("register2", "false");
            c.setMaxAge(10800);
            c.setPath("/");
            response.addCookie(c);
        }
        response.getWriter().write(res.toString());
    }
}
