package com.seu.drunkdog.controller;

import com.seu.drunkdog.mapper.TagMapper;
import com.seu.drunkdog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@Service
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TagMapper tagMapper;
    @RequestMapping("/getInitialTag")
    public void getInitialTag(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String[] initialTagArray = request.getParameterValues("initialTagArray");
        int user_id = Integer.parseInt(request.getParameter("user_id"));
//        System.out.println(initialTagArray.length);
        for(int i = 0; i < initialTagArray.length; i++){
            int user_tag = userService.getIdByTag(initialTagArray[i]);
            userService.InsertUserTag(user_id, user_tag, 1);
            res.put("tag",user_tag);
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/clickToSaveTag")
    public void clickToSaveTag(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String newTag = request.getParameter("newTag");
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        if(tagMapper.getIdByCategory(newTag) == 0){
            tagMapper.saveCategory(newTag);
            int user_tag = userService.getIdByTag(newTag);
            userService.InsertUserTag(user_id, user_tag, 0.4);
            res.put("newTag", newTag);
        }
        else{
            userService.updateUserWeight(user_id);
            res.put("oldTag", newTag);
        }
        response.getWriter().write(res.toString());
    }
}
