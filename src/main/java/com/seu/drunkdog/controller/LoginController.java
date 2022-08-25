package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.service.UserService;
import com.seu.drunkdog.tool.createToken;
import com.seu.drunkdog.tool.testToken;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
//@RequestMapping("/user")
@Slf4j
@Service
@ResponseBody
@CrossOrigin(origins = "http://localhost:8080",maxAge = 36000)
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    createToken tokenService;
    @Autowired
    testToken tokenService2;
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

//            hs.setAttribute("name",name);
            Cookie c=new Cookie("name",name);
            c.setMaxAge(10800);
            c.setPath("/");
            Cookie d=new Cookie("islogin","true");
            d.setMaxAge(10800);
            d.setPath("/");
            String token = tokenService.getToken(s);
            res.put("token", token);
//            Claims claims = Jwts.parser()
//                    .setSigningKey("my-123")
//                    .parseClaimsJws(token)
//                    .getBody();

//            if(name.equals("admin@qq.com"))
//            {
//                Cookie f=new Cookie("service","true");
//                f.setMaxAge(10800);
//                f.setPath("/");
//                response.addCookie(f); res.put("service","true");
//            }
            response.addCookie(c);
            response.addCookie(d);

            res.put("user_id", s.getId());
            res.put("msg","true");
            res.put("code", 0);
        }
        else {
            res.put("msg","false");
            res.put("code", 1);
            Cookie c=new Cookie("name",name);
            c.setMaxAge(10800);
            c.setPath("/");
            response.addCookie(c);
        }

        response.getWriter().write(res.toString());
    }
    @RequestMapping("/checkToken")
    public Object checkToken(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String token = request.getParameter("token");
//        String token = request.getHeader("token");
        return tokenService2.tokenSign(token);

//        return tokenService.checkToken(token);
//        return tokenService2.tokenSign(token);
    }


//    @RequestMapping("/getverifycode")
//    public void generatecode(HttpServletRequest request, HttpServletResponse response)throws Exception
//    {
//        HttpSession hs=request.getSession();
//        File dir = new File("/usr/local/resource/verifycode");
//        int w = 250, h = 80;
//        String verifyCode = VerifyCode.generateVerifyCode(5);
//        File file = new File(dir, UUID.randomUUID() + ".jpg");
//        String path="http://localhost:8080/"+file.getName();
//        hs.setAttribute("verifycode",verifyCode);
//        VerifyCode.outputImage(w, h, file,verifyCode);
//        System.out.println(hs.getId());
//        JSONObject res=new JSONObject();
//        res.put("msg",path);
//        response.getWriter().write(res.toString());
//    }
}
