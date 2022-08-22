package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.entity.UserTag;
import com.seu.drunkdog.mapper.TagMapper;
import com.seu.drunkdog.service.TagService;
import com.seu.drunkdog.service.UserService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Service
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @RequestMapping("/getInitialTag")
    public void getInitialTag(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String[] initialTagArray = request.getParameterValues("initialTagArray");
        int user_id = Integer.parseInt(request.getParameter("user_id"));
//        System.out.println(initialTagArray.length);
        for(int i = 0; i < initialTagArray.length; i++){
            int user_tag = userService.searchIdByTag(initialTagArray[i]);
            userService.InsertUserTag(user_id, user_tag, 3);
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
        if(tagService.searchIdByTag(newTag) == 0){
            tagService.insertTag(newTag);
            int user_tag = userService.searchIdByTag(newTag);
            userService.InsertUserTag(user_id, user_tag, 1);
            res.put("newTag", newTag);
        }
        else{
            userService.updateUserWeight(user_id);
            res.put("oldTag", newTag);
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/drawUserTagCake")
    public void drawUserTagCake(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        List<UserTag> allTagByUserId = userService.searchAllById(user_id);
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allTagByUserId.size(); i++) {
            String user_tag = tagService.searchTagById(allTagByUserId.get(i).getUser_tag());
            JSONObject temp = new JSONObject();
            temp.put(user_tag,allTagByUserId.get(i).getUser_weight());
            ja.add(temp);
        }
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/categoryMovie")
    public void categoryMovie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        String tag = request.getParameter("tag");
        int category = tagService.searchIdByTag(tag);
        System.out.println(category);
        List<Movie> allMovieByTag = userService.searchAllMovieByTag(category);
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovieByTag.size(); i++) {
//            JSONObject temp = new JSONObject();
            ja.add(JSONObject.fromObject(allMovieByTag.get(i).getAll()));
//            ja.add(temp);
        }
        JSONObject res = new JSONObject();
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());
    }



}
