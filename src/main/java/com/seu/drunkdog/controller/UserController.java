package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.entity.UserTag;
import com.seu.drunkdog.service.TagService;
import com.seu.drunkdog.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Service
@CrossOrigin(origins = "http://localhost:8080",maxAge = 36000)
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
        res.put("code", 0);
        res.put("msg", "true");
        for(int i = 0; i < initialTagArray.length; i++){
            int user_tag = userService.searchIdByTag(initialTagArray[i]);
            userService.InsertUserTag(user_id, user_tag, 5);
        }
        response.getWriter().write(res.toString());
    }
//    @RequestMapping("/clickToSaveTag")
//    public void clickToSaveTag(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        HttpSession hs = request.getSession();
//        response.setCharacterEncoding("UTF-8");
//        JSONObject res = new JSONObject();
//        String newTag = request.getParameter("newTag");
//        int user_id = Integer.parseInt(request.getParameter("user_id"));
////        if(tagService.searchIdByTag(newTag) == 0){
////            tagService.insertTag(newTag);
////            int user_tag = userService.searchIdByTag(newTag);
////            userService.InsertUserTag(user_id, user_tag, 1);
////            res.put("newTag", newTag);
////        }
////        else{
////            userService.updateUserWeight(user_id);
////            res.put("oldTag", newTag);
////        }
//        res.put("code", 0);
//        res.put("msg", "true");
//        response.getWriter().write(res.toString());
//    }
    @RequestMapping("/drawUserTagCake")
    public void drawUserTagCake(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        List<UserTag> allTagByUserId = userService.searchAllById(user_id);
        if(allTagByUserId.size() == 0){
            int weightTotal = 0;
            for(int i = 0; i < allTagByUserId.size(); i++){
                weightTotal += allTagByUserId.get(i).getUserWeight();
            }
            JSONArray ja = new JSONArray();
            for (int i = 0; i < allTagByUserId.size(); i++) {
                String user_tag = tagService.searchTagById(allTagByUserId.get(i).getUserTag());
                JSONObject temp = new JSONObject();
                temp.put("category", user_tag);
                temp.put("weight", (allTagByUserId.get(i).getUserWeight() + 0.0)/weightTotal);
                ja.add(temp);
            }
            res.put("data", ja.toString());
            res.put("code", 0);
            res.put("msg", "true");
        }
        else{
            res.put("code", 1);
            res.put("msg", "false");
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/categoryMovie")
    public void categoryMovie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        String tag = request.getParameter("tag");
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        JSONObject res = new JSONObject();
        int user_tag = userService.searchIdByTag(tag);
        if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
            userService.InsertUserTag(user_id, user_tag, 3);
        }
        else{
            userService.updateUserWeightByIdAndTag(user_id, user_tag, 3);
        }
//        if(tagService.searchIdByTag(tag) == 0){
//            tagService.insertTag(tag);
//            int user_tag = userService.searchIdByTag(tag);
//            userService.InsertUserTag(user_id, user_tag, 2);
////            res.put("newTag", tag);
//
//        }
//        else{
//            userService.updateUserWeight(user_id);
//        }
        int category = tagService.searchIdByTag(tag);
//        System.out.println(category);
        List<Movie> allMovieByTag = userService.searchAllMovieByTag(category);
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovieByTag.size(); i++) {
            ja.add(JSONObject.fromObject(allMovieByTag.get(i)));
        }

        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

}
