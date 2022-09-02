package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.service.UserService;
import com.seu.drunkdog.tool.createToken;
import com.seu.drunkdog.tool.testToken;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@Service
@CrossOrigin(origins = "http://localhost:8081",maxAge = 36000)
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    createToken tokenService;
    @Autowired
    testToken tokenService2;
    //用户登陆接口，用户登陆之后会返回给用户一个token，前端保存
    @RequestMapping("/login")
    public void login(@RequestBody User u, HttpServletResponse response)throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String name = u.getName();
        String password = u.getPassword();
        User s = userService.LoginIn(name);
        if(s!=null&&s.getPassword().equals(password)) {
            if(userService.judgeIfFirstLogin(name, password) == 0){
                userService.updateUserIfFirstLogin(name, password);
                res.put("msg","首次登陆");
            }
            else{
                res.put("msg","非首次登陆");
            }
            String token = tokenService.getToken(s);
            res.put("token", token);
            res.put("code", 200);
        }
        else {
            res.put("msg","false");
            res.put("code", 100);
        }

        response.getWriter().write(res.toString());
    }
    //验证token接口，前端需要userId时需要发送token然后后端返回userId
    @RequestMapping("/checkToken")
    public Object checkToken(HttpServletRequest request)throws Exception{
        String token = request.getHeader("token");
        return tokenService2.tokenSign(token);
    }
}
