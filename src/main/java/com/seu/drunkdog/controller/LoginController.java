package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.service.UserService;
import com.seu.drunkdog.tool.VerifyCode;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.UUID;

@RestController
//@RequestMapping("/user")
@Slf4j
@Service
public class LoginController {
    @Autowired
    UserService userService;
//    @Autowired
//    VerifyCode verifyCode;
//    @RequestMapping("/login")
//    public String show(){return "login";}
//    @RequestMapping("/register")
//    @RequestMapping("/insert")
//    public String register(String name, String password){
//        userService.InsertUser(name, password);
//        return "success";
//    }
//    @RequestMapping("/loginIn")
//    public String login(String name, String password){
//        User user = userService.LoginIn(name);
//        log.info("name:{}",name);
//        log.info("password:{}",password);
////        String res = verifyCode.generateVerifyCode(4);
////        System.out.println(res);
//        if(user!=null){
//            return "success";
//        }
//        else {
//            return "error";
//        }
//    }
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
//        String code = request.getParameter("code");
//        System.out.println("user:"+name+" password:"+password);
        User s = userService.LoginIn(name);
//        System.out.println(code);
//        if(code.equals(hs.getAttribute("verifycode"))){
//            res.put("success","true");
//        }else {
//            res.put("success","false");
//            res.put("msg","false");response.getWriter().write(res.toString());
//            return;
//        }
        if(s!=null&&s.getPassword().equals(password)) {

            hs.setAttribute("name",name);
            Cookie c=new Cookie("name",name);
            c.setMaxAge(10800);
            c.setPath("/");
            Cookie d=new Cookie("islogin","true");
            d.setMaxAge(10800);
            d.setPath("/");
            if(name.equals("admin@qq.com"))
            {
                Cookie f=new Cookie("service","true");
                f.setMaxAge(10800);
                f.setPath("/");
                response.addCookie(f); res.put("service","true");
            }
            response.addCookie(c);
            response.addCookie(d);
            res.put("msg","true");

        }
        else {
            res.put("msg","false");
        }

        response.getWriter().write(res.toString());
    }

//    @RequestMapping("/getverifycode")
//    public void generatecode(HttpServletRequest request, HttpServletResponse response)throws Exception
//    {
//        HttpSession hs=request.getSession();
//        File dir = new File("/usr/local/resource/verifycode");
//        int w = 250, h = 80;
//        String verifyCode = VerifyCode.generateVerifyCode(5);
//        File file = new File(dir, UUID.randomUUID() + ".jpg");
//        String path="http://119.3.176.243/"+file.getName();
//        hs.setAttribute("verifycode",verifyCode);
//        VerifyCode.outputImage(w, h, file,verifyCode);
//        System.out.println(hs.getId());
//        JSONObject res=new JSONObject();
//        res.put("msg",path);
//        response.getWriter().write(res.toString());
//    }
}
