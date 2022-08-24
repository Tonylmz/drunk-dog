package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.service.MovieService;
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

import static java.lang.Math.min;

@Service
@RestController
@CrossOrigin(origins = "http://localhost:8080",maxAge = 36000)
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;
    @RequestMapping("/getTopMovie")
    public void getTopMovie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
        List<Movie> allMovie = movieService.searchAllMovie();
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovie.size(); i++) {
            ja.add(JSONObject.fromObject(allMovie.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("msg", "true");
        res.put("code", 0);
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showTopMovie")
    public void showTopMovie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        Movie movie = movieService.searchTopMovie(movie_id);
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length - 1];

        for(int i = 1;i < intCateGory.length; i++){
            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result=String.join("|", stringCategory);
        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());

    }
    @RequestMapping("/showMovie")
    public void showMovie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        Movie movie = movieService.searchMovie(movie_id);
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length - 1];
        List<String> allMovieCommentById = movieService.searchMovieCommentById(movie_id);
        for (int i = 0; i < min(allMovieCommentById.size(),5); i++) {
            ja.add(allMovieCommentById.get(i));
        }
        for(int i = 1;i < intCateGory.length; i++){
            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result=String.join("|", stringCategory);
        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");

        response.getWriter().write(res.toString());
    }
    @RequestMapping("/movieCommentByUser")
    public void movieCommentByUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        String movie_comment = request.getParameter("movie_comment");
        movieService.insertMovieComment(movie_id, movie_comment);
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/getMovieComment")
    public void getMovieComment(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
//        int movie_score = Integer.parseInt(request.getParameter("movie_score"));
//        int user_id = Integer.parseInt(request.getParameter("user_id"));
        Movie movie = movieService.searchMovie(movie_id);
//        String[] intCateGory = movie.getCategory().split("\\|");
//        for(int i = 1;i < intCateGory.length; i++){
//            if(userService.ifNullFindByIdAndTag(user_id, Integer.parseInt(intCateGory[i])) == null){
//                userService.InsertUserTag(user_id, Integer.parseInt(intCateGory[i]), movie_score - 3);
//            }
//            else{
//                userService.updateUserWeightByIdAndTag(user_id, Integer.parseInt(intCateGory[i]), movie_score - 3);
//            }
//        }
        List<String> allMovieCommentById = movieService.searchMovieCommentById(movie_id);
        int size = allMovieCommentById.size();
//        int page = Integer.parseInt(request.getParameter("page"));
//        int pageSize = 10;
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovieCommentById.size(); i++) {
            ja.add(allMovieCommentById.get(i));
        }
        JSONObject jo1 = new JSONObject();
        jo1.put("size", size);
        ja.add(jo1);
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/getUserScore")
    public void getUserScore(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        int movie_score = Integer.parseInt(request.getParameter("movie_score"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        Movie movie = movieService.searchMovie(movie_id);
        String[] intCateGory = movie.getCategory().split("\\|");
        movieService.insertMovieScore(movie_id, movie_score);
        for(int i = 1;i < intCateGory.length; i++){
            if(userService.ifNullFindByIdAndTag(user_id, Integer.parseInt(intCateGory[i])) == null){
                userService.InsertUserTag(user_id, Integer.parseInt(intCateGory[i]), movie_score - 3);
            }
            else{
                userService.updateUserWeightByIdAndTag(user_id, Integer.parseInt(intCateGory[i]), movie_score - 3);
            }
        }
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
        //推荐
    }


    @RequestMapping("/showMovieBySearch")
    public void showMovieBySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        //此处需要python函数分词
        String name = request.getParameter("name");
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        List<Movie> allMovieBySearchName = movieService.searchMovieByName(name);
        JSONObject res = new JSONObject();
        JSONArray ja = new JSONArray();
        if(allMovieBySearchName.size() == 0){
            List<Movie> allMovieBySearchDirectorOrActor = movieService.searchMovieByDirectorOrActor(name);
            if(allMovieBySearchDirectorOrActor.size() == 0){
                res.put("code", 1);
                res.put("msg", "false");
            }
            else{
                int user_tag;
                if(tagService.searchIdByTag(name) == 0){
                    tagService.insertTag(name);
                }
                user_tag = tagService.searchIdByTag(name);
                if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
                    userService.InsertUserTag(user_id, user_tag, 2);
                }
                else{
                    userService.updateUserWeightByIdAndTag(user_id, user_tag, 2);
                }
                for (int i = 0; i < allMovieBySearchDirectorOrActor.size(); i++) {
                    ja.add(JSONObject.fromObject(allMovieBySearchDirectorOrActor.get(i)));
                }
                res.put("data", ja.toString());
                res.put("code", 0);
                res.put("msg", "directorOrActor");
            }
        }
        else{
            for(int i = 0;i < allMovieBySearchName.size(); i++){
                String[] intCateGory = allMovieBySearchName.get(i).getCategory().split("\\|");
                for(int j = 1;j < intCateGory.length; j++){
                    if(userService.ifNullFindByIdAndTag(user_id, Integer.parseInt(intCateGory[j])) == null){
                        userService.InsertUserTag(user_id, Integer.parseInt(intCateGory[j]), 1);
                    }
                    else{
                        userService.updateUserWeightByIdAndTag(user_id, Integer.parseInt(intCateGory[j]), 1);
                    }
                }
                String firstActor = allMovieBySearchName.get(i).getActor().split("/")[0];
                int user_tag;
                if(tagService.searchIdByTag(firstActor) == 0){
                    tagService.insertTag(firstActor);
                }
                user_tag = tagService.searchIdByTag(firstActor);
                if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
                    userService.InsertUserTag(user_id, user_tag, 1);
                }
                else{
                    userService.updateUserWeightByIdAndTag(user_id, user_tag, 1);
                }
            }
            for (int i = 0; i < allMovieBySearchName.size(); i++) {
                ja.add(JSONObject.fromObject(allMovieBySearchName.get(i)));
            }
            res.put("data", ja.toString());
            res.put("code", 0);
            res.put("msg", "movieName");
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showAugustMovie")
    public void showAugustMovie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        List<Movie> allAugustMovie= movieService.getAllAugustMovie();
        for (int i = 0; i < allAugustMovie.size(); i++) {
            ja.add(JSONObject.fromObject(allAugustMovie.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/showAugustMovieDetail")
    public void showAugustMovieDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        Movie movie = movieService.searchAugustMovie(movie_id);
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length - 1];
        for(int i = 1;i < intCateGory.length; i++){
            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result=String.join("，", stringCategory);
        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/regionBox")
    public void regionBox(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        JSONObject[] ja2 = new JSONObject[5];
        for(int i = 0;i < 5;i++){
            ja2[i] = new JSONObject();
        }
        ja2[0].put("美国", movieService.numOfAmericanMovie());
        ja2[1].put("中国", movieService.numOfChineseMovie());
        ja2[2].put("法国", movieService.numOfFrenchMovie());
        ja2[3].put("英国", movieService.numOfEnglishMovie());
        ja2[4].put("德国", movieService.numOfGermanMovie());
        JSONObject res = new JSONObject();
        for(int i = 0; i < 5; i++){
            ja.add(JSONObject.fromObject(ja2[i]));
        }
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());

    }
    @RequestMapping("/regionBoxAmerica")
    public void regionBoxAmerica(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        List<Movie> top5MovieAmerica = movieService.getMovieAmerica();
        for (int i = 0; i < 5; i++) {
            ja.add(JSONObject.fromObject(top5MovieAmerica.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/regionBoxChina")
    public void regionBoxChina(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        List<Movie> top5MovieChina = movieService.getMovieChina();
        for (int i = 0; i < 5; i++) {
            ja.add(JSONObject.fromObject(top5MovieChina.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/regionBoxEngland")
    public void regionBoxEngland(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        List<Movie> top5MovieEngland = movieService.getMovieEngland();
        for (int i = 0; i < 5; i++) {
            ja.add(JSONObject.fromObject(top5MovieEngland.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/regionBoxFrance")
    public void regionBoxFrance(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        List<Movie> top5MovieFrance = movieService.getMovieFrance();
        for (int i = 0; i < 5; i++) {
            ja.add(JSONObject.fromObject(top5MovieFrance.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/regionBoxGermany")
    public void regionBoxGermany(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        List<Movie> top5MovieGermany = movieService.getMovieGermany();
        for (int i = 0; i < 5; i++) {
            ja.add(JSONObject.fromObject(top5MovieGermany.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
}
