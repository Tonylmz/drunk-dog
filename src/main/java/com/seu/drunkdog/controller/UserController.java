package com.seu.drunkdog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seu.drunkdog.entity.*;
import com.seu.drunkdog.service.MovieService;
import com.seu.drunkdog.service.TagService;
import com.seu.drunkdog.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Service
@CrossOrigin(origins = "http://localhost:8081",maxAge = 36000)
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    MovieService movieService;
    @RequestMapping("/getInitialTag")
    public void getInitialTag(@RequestBody InitialTag initialTag, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        String[] initialTagArray = initialTag.getInitialTagArray();
        int user_id = initialTag.getUserId();
        if(initialTagArray.length == 0){
            res.put("code", 100);
            res.put("msg", "标签为空");
        }
        else{
            for(int i = 0; i < initialTagArray.length; i++){
                int user_tag = userService.searchIdByTag(initialTagArray[i]);
                if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
                    userService.InsertUserTag(user_id, user_tag, 5);
                }
                else{
                    userService.updateUserWeightByIdAndTag(user_id, user_tag, 5);
                }
            }
            res.put("code", 200);
            res.put("msg", "标签不为空");
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/recommendByUser")
    public void recommendByUser(@RequestBody UserTag userTag, HttpServletResponse response) throws Exception{
        int user_id = userTag.getUserId();
        userService.deleteAllFromUserPython();


        String arg = "python main.py --user-id " + String.valueOf(user_id);
        Process proc = Runtime.getRuntime().exec(arg);

        Thread.sleep(2000);

        List<Integer> recommendByUser = userService.getAllFromUserPython();
        JSONArray ja = new JSONArray();

        for(int i = 0;i < 10;i++){
            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
        }
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());

        res.put("code", 200);
        res.put("msg", "true");

        response.getWriter().write(res.toString());
    }
    @RequestMapping("/drawUserTagCake")
    public void drawUserTagCake(@RequestBody UserTag userTag, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONObject res = new JSONObject();
        int user_id = userTag.getUserId();
        List<UserTag> allTagByUserId = userService.searchAllById(user_id);
        if(allTagByUserId.size() != 0){
            int weightTotal = 0;
            for(int i = 0; i < allTagByUserId.size(); i++){
                weightTotal += allTagByUserId.get(i).getUserWeight();
            }
            JSONArray ja = new JSONArray();
            for (int i = 0; i < allTagByUserId.size(); i++) {
                String user_tag = tagService.searchTagById(allTagByUserId.get(i).getUserTag());
                JSONObject temp = new JSONObject();
                temp.put("category", user_tag);
                temp.put("value", (allTagByUserId.get(i).getUserWeight() + 0.0)/weightTotal);
                ja.add(temp);
            }
            res.put("data", ja.toString());
            res.put("code", 200);
            res.put("msg", "true");
        }
        else{
            res.put("code", 100);
            res.put("msg", "false");
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/categoryMovie")
    public void categoryMovie(@RequestBody CategoryTag categoryTag, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        String tag = categoryTag.getTag();
        int user_id = categoryTag.getUserId();
        JSONObject res = new JSONObject();
        int user_tag = userService.searchIdByTag(tag);
        if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
            userService.InsertUserTag(user_id, user_tag, 3);
        }
        else{
            userService.updateUserWeightByIdAndTag(user_id, user_tag, 3);
        }
        int category = tagService.searchIdByTag(tag);
        System.out.println(category);
        Page<Movie> page = new Page<>(categoryTag.getPageNo(),12);
        IPage<Movie> iPage = movieService.getMovieByCategoryAndPage(page, category);
        JSONObject jo = new JSONObject();
        jo.put("detail", iPage);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

}
