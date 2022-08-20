package com.seu.drunkdog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class ExitController {
    @RequestMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession hs=request.getSession();
        for(int i=0;i<request.getCookies().length;i++)
        {
            if(request.getCookies()[i].getName().equals("name"))
            {
                Cookie ck=request.getCookies()[i];
                ck.setMaxAge(0);
                response.addCookie(ck);
            }
            if(request.getCookies()[i].getName().equals("service"))
            {
                Cookie ck=request.getCookies()[i];
                ck.setMaxAge(0);
                response.addCookie(ck);
            }

        }
        try {
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
