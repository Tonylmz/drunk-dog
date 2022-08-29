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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@Service
@RestController
@CrossOrigin(origins = "http://localhost:8081",maxAge = 36000)
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @RequestMapping("/getTopMovie")
    public void getTopMovie(@RequestBody MovieComment movieComment, HttpServletResponse response) throws Exception {
//        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
//        List<Movie> allMovie = movieService.searchAllTopMovie();
//        JSONArray ja = new JSONArray();
//        for (int i = 0; i < allMovie.size(); i++) {
//            ja.add(JSONObject.fromObject(allMovie.get(i)));
//        }
//        response.setCharacterEncoding("UTF-8");
//        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Page<Movie> page = new Page<>(movieComment.getPageNo(),10);
        IPage<Movie> iPage = movieService.searchAllTopMovie(page);
        JSONObject res = new JSONObject();
//        res.put("data", ja.toString());
//        System.out.println(iPage.getRecords().get(0).getPictureLink());
        res.put("data", iPage);
        res.put("msg", "true");
        res.put("code", 200);
        response.getWriter().write(res.toString());

    }
    @RequestMapping("/showTopMovie")
    public void showTopMovie(@RequestBody Movie m, HttpServletResponse response) throws Exception{
//        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
//        int id = Integer.parseInt(request.getParameter("id"));
        Movie movie = movieService.searchTopMovie(m.getMovieId());
        int user_id = m.getUserId();
//        String[] intCateGory = movie.getCategory().split("\\|");
//        String[] stringCategory = new String[intCateGory.length - 1];
//
//        for(int i = 1;i < intCateGory.length; i++){
//            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
//        }
//        String result=String.join("|", stringCategory);
//        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
//        res.put("data", ja.toString());
//        res.put("code", 200);
//        res.put("msg", "true");
//        response.getWriter().write(res.toString());
        //
//        String[] args1 = new String[]{"python", "E:\\Study\\intern\\main.py"};
        movieService.deleteAllFromMoviePython();
        userService.deleteAllFromUserPython();


        String arg = "python E:\\Study\\intern\\main.py --user-id " + String.valueOf(user_id) + " --movie-id " +String.valueOf(m.getMovieId());
//        String[] args1 = new String[]{"python", "main.py", arg};

//        System.out.println(arg);
        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(5000);

        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
        List<Integer> recommendByUser = userService.getAllFromUserPython();
//        System.out.println(recommendByMovie.size());
//        System.out.println(recommendByUser.size());
//        List<Movie> recommendMovie = new ArrayList<>(10);
//        List<Movie> recommendUser = new ArrayList<>(10);
//        movieService.searchTopMovie(1).getMovieId();
        JSONArray ja1 = new JSONArray();
        JSONArray ja2 = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
        }

        for(int i = 0;i < 10;i++){
            ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
        }
        JSONObject jo = new JSONObject();
        jo.put("detail", ja);
        jo.put("recommendByMovie", ja1);
        jo.put("recommendByUser", ja2);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
        //

    }
    @RequestMapping("/showMovie")
    public void showMovie(@RequestBody Movie m, HttpServletResponse response) throws Exception{
//        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
//        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
//        int movie_id = Integer.parseInt
//        (request.getParameter("movie_id"));
        int movie_id = m.getMovieId();
        int user_id = m.getUserId();
        Movie movie = movieService.searchMovie(movie_id);
        String[] intCateGory = movie.getCategory().split("\\|");
//        System.out.println(intCateGory[1]);
        String[] stringCategory = new String[intCateGory.length - 1];
//        List<MovieComment> allMovieCommentById = movieService.searchMovieCommentById(movie_id);

//        for (int i = 0; i < min(allMovieCommentById.size(),5); i++) {
//            ja.add(allMovieCommentById.get(i));
//        }
        for(int i = 1;i < intCateGory.length; i++){
            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result=String.join("，", stringCategory);
        movie.setCategory(result);
//        JSONObject temp = new JSONObject();
//        temp.put("detail", movie);
//        ja.add(JSONObject.fromObject(temp));
        jo.put("detail", movie);
        JSONObject res = new JSONObject();


        //
        movieService.deleteAllFromMoviePython();
        userService.deleteAllFromUserPython();


        String arg = "python E:\\Study\\intern\\main.py --user-id " + String.valueOf(user_id) + " --movie-id " +String.valueOf(m.getUserId());
//        String[] args1 = new String[]{"python", "main.py", arg};
        Process proc = Runtime.getRuntime().exec(arg);

        Thread.sleep(5000);

        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
        List<Integer> recommendByUser = userService.getAllFromUserPython();
        JSONArray ja1 = new JSONArray();
        JSONArray ja2 = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
        }

        for(int i = 0;i < 10;i++){
            ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
        }
//        JSONObject temp1 = new JSONObject();
//        JSONObject temp2 = new JSONObject();
//        temp1.put("recommendByMovie", ja1);
//        temp2.put("recommendByUser", ja2);
        jo.put("recommendByMovie", ja1);
        jo.put("recommendByUser", ja2);
//        ja.add(temp1);
//        ja.add(temp2);
//        res.put("data", ja.toString());
        res.put("data", jo.toString());
        res.put("code", 200);
        res.put("msg", "true");

        response.getWriter().write(res.toString());






        //
    }
    @RequestMapping("/movieCommentByUser")
    public void movieCommentByUser(@RequestBody MovieComment movieComment, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = movieComment.getMovieId();
        String movie_comment = movieComment.getMovieComment();
//        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
//        String movie_comment = request.getParameter("movie_comment");
        movieService.insertMovieComment(movie_id, movie_comment);
        JSONObject res = new JSONObject();
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/getMovieComment")
    public void getMovieComment(@RequestBody MovieComment movieComment, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = movieComment.getMovieId();
        int pageNo = movieComment.getPageNo();
//        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
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
//        List<MovieComment> allMovieCommentById = movieService.searchMovieCommentById(movie_id);
//        int size = allMovieCommentById.size();
////        int page = Integer.parseInt(request.getParameter("page"));
////        int pageSize = 10;
//        JSONArray ja = new JSONArray();
//        for (int i = 0; i < allMovieCommentById.size(); i++) {
//            ja.add(allMovieCommentById.get(i));
//        }
//        JSONObject jo1 = new JSONObject();
//        jo1.put("size", size);
//        ja.add(jo1);
//        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Page<MovieComment> page = new Page<>(pageNo, 10);
        IPage<MovieComment> iPage = movieService.searchMovieCommentByIdAndPage(movie_id, page);
        JSONObject res = new JSONObject();
//        res.put("data", ja.toString());
        res.put("data", iPage);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/getUserScore")
    public void getUserScore(@RequestBody MovieComment movieComment, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = movieComment.getMovieId();
        double movie_score = movieComment.getScore();
        int user_id = movieComment.getUserId();
//        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
//        int movie_score = Integer.parseInt(request.getParameter("movie_score"));
//        int user_id = Integer.parseInt(request.getParameter("user_id"));
        Movie movie = movieService.searchMovie(movie_id);
        String[] intCateGory = movie.getCategory().split("\\|");
//        movieService.insertMovieScore(movie_id, movie_score);
        for(int i = 1;i < intCateGory.length; i++){
            if(userService.ifNullFindByIdAndTag(user_id, Integer.parseInt(intCateGory[i])) == null){
                userService.InsertUserTag(user_id, Integer.parseInt(intCateGory[i]), movie_score - 3);
            }
            else{
                userService.updateUserWeightByIdAndTag(user_id, Integer.parseInt(intCateGory[i]), movie_score - 3);
            }
        }
        JSONObject res = new JSONObject();


        //推荐




        userService.deleteAllFromUserPython();


        String arg = "python E:\\Study\\intern\\main.py --user-id " + String.valueOf(user_id);
//        String[] args1 = new String[]{"python", "main.py", arg};

        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(5000);

//        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
        List<Integer> recommendByUser = userService.getAllFromUserPython();
        JSONArray ja = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
        }

//        for(int i = 0;i < 10;i++){
//            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
//        }
        JSONObject jo = new JSONObject();
        jo.put("recommendByUser", ja);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");

        response.getWriter().write(res.toString());


        //
    }

    @RequestMapping("/clickLike")
    public void clickLike(@RequestBody Movie movie, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
//        int id = Integer.parseInt(request.getParameter("id"));
        movieService.addMovieLike(movie.getId());
        JSONObject res = new JSONObject();
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/showMovieBySearch")
    public void showMovieBySearch(@RequestBody Search search, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");

//        movieService.insertResult(search.getName());
        //此处需要python函数分词
        String name = "--text " + search.getName();
        int user_id = search.getUserId();
//        userService.InsertUser(name, "我喜欢");
        String args1 = "python E:\\Study\\intern\\hmm.py " + name;
//        System.out.println(args1);
        movieService.deleteAllResult();
        Process proc = Runtime.getRuntime().exec(args1);
        Thread.sleep(5000);
//        BufferedReader in = new BufferedReader(new InputStreamReader( proc.getInputStream() ));
//        String actionStr = in.readLine();
//        if (actionStr != null)
//            System.out.println(actionStr);
//        System.out.println("我喜欢");
//
//        in.close();
//        String[] out = actionStr.split(" ");

        List<HmmResult> out = movieService.findResult();
//        System.out.println(out.size());
//
//        System.out.println(out.get(0).getResult());
        int k = -1;
        for(int i = 0;i < out.size();i++){
            List<Movie> allMovieBySearchName = movieService.searchMovieByName(out.get(i).getResult());
            if(allMovieBySearchName.size()!= 0){
                k = i;
                break;
            }
        }
        JSONObject res = new JSONObject();
        JSONArray ja = new JSONArray();
//        res.put("标签", out.get(k).getResult());
//        System.out.println(k);
        if(k == -1){
            for(int i = 0;i < out.size();i++){
                List<Movie> allMovieBySearchDirectorOrActor = movieService.searchMovieByDirectorOrActor(out.get(i).getResult());
                if(allMovieBySearchDirectorOrActor.size()!= 0){
                    k = i;
                    break;
                }
            }
            if(k == -1){
                res.put("code", 100);
                res.put("msg", "false");
            }
            else{
                String name2 = out.get(k).getResult();
                List<Movie> allMovieBySearchDirectorOrActor = movieService.searchMovieByDirectorOrActor(out.get(k).getResult());
//                int user_tag;
//                if(tagService.searchIdByTag(name2) == 0){
//                    tagService.insertTag(name2);
//                }
//                user_tag = tagService.searchIdByTag(name2);
//                if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
//                    userService.InsertUserTag(user_id, user_tag, 2);
//                }
//                else{
//                    userService.updateUserWeightByIdAndTag(user_id, user_tag, 2);
//                }
                for (int i = 0; i < allMovieBySearchDirectorOrActor.size(); i++) {
                    ja.add(JSONObject.fromObject(allMovieBySearchDirectorOrActor.get(i)));
                }
                userService.deleteAllFromUserPython();
                movieService.deleteAllFromMoviePython();
//                System.out.println(allMovieBySearchDirectorOrActor.size());
                String arg = "python E:\\Study\\intern\\main.py --movie-id " + String.valueOf(allMovieBySearchDirectorOrActor.get(0).getMovieId()) + "--user-id" + String.valueOf(user_id);
//                String[] args2 = new String[]{"python", "main.py", arg};
                Process proc2 = Runtime.getRuntime().exec(arg);

                Thread.sleep(5000);

                List<Integer> recommendByUser = userService.getAllFromUserPython();

                List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
                JSONArray ja1 = new JSONArray();
                for(int i = 0;i < 10;i++){
                    ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
                }
                JSONArray ja2 = new JSONArray();
                for(int i = 0;i < 10;i++){
                    ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
                }
                JSONObject jo1 = new JSONObject();
                jo1.put("detail", ja);
                jo1.put("recommendByUser", ja1);
                jo1.put("recommendByMovie", ja2);
                res.put("data", jo1);
                res.put("code", 200);
                res.put("msg", "directorOrActor");
            }
        }
        else{
            List<Movie> allMovieBySearchName = movieService.searchMovieByName(out.get(k).getResult());
//            System.out.println(allMovieBySearchName.size());
            for(int i = 0;i < allMovieBySearchName.size(); i++){
                String[] intCateGory = allMovieBySearchName.get(i).getCategory().split("\\|");
//                System.out.println(intCateGory.length);
                for(int j = 1;j < intCateGory.length; j++){
                    if(userService.ifNullFindByIdAndTag(user_id, Integer.parseInt(intCateGory[j])) == null){
                        userService.InsertUserTag(user_id, Integer.parseInt(intCateGory[j]), 1);
                    }
                    else{
                        userService.updateUserWeightByIdAndTag(user_id, Integer.parseInt(intCateGory[j]), 1);
                    }
                }
//                String firstActor = allMovieBySearchName.get(i).getActor().split("/")[0];
//                int user_tag;
//                if(tagService.searchIdByTag(firstActor) == 0){
//                    tagService.insertTag(firstActor);
//                }
//                user_tag = tagService.searchIdByTag(firstActor);
//                if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
//                    userService.InsertUserTag(user_id, user_tag, 1);
//                }
//                else{
//                    userService.updateUserWeightByIdAndTag(user_id, user_tag, 1);
//                }
            }
//            System.out.println(1111);
            for (int i = 0; i < allMovieBySearchName.size(); i++) {
                ja.add(JSONObject.fromObject(allMovieBySearchName.get(i)));
            }
            userService.deleteAllFromUserPython();
            movieService.deleteAllFromMoviePython();
//            System.out.println(allMovieBySearchName.get(0).getMovieId());
            String arg = "python E:\\Study\\intern\\main.py --user-id " + String.valueOf(user_id) + " --movie-id " + String.valueOf(allMovieBySearchName.get(0).getMovieId());
//            System.out.println(arg);
//            String[] args2 = new String[]{"python", "main.py", arg};
            Process proc2 = Runtime.getRuntime().exec(arg);

            Thread.sleep(5000);

            List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
            List<Integer> recommendByUser = userService.getAllFromUserPython();

//            System.out.println(recommendByMovie.size());
//            System.out.println(recommendByUser.size());
            JSONArray ja1 = new JSONArray();
            for(int i = 0;i < 10;i++){
                ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
            }
            JSONArray ja2 = new JSONArray();
            for(int i = 0;i < 10;i++){
                ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
            }
            JSONObject jo = new JSONObject();
            jo.put("detail", ja);
            jo.put("recommendByMovie", ja1);
            jo.put("recommendByUser", ja2);
            res.put("data", jo);
            res.put("code", 201);
            res.put("msg", "movieName");
        }
//        String name2 = out[k];
//
//
//
//
//
//
////        int user_id = search.getUserId();
////        String name = request.getParameter("name");
////        int user_id = Integer.parseInt(request.getParameter("user_id"));
//        List<Movie> allMovieBySearchName = movieService.searchMovieByName(name2);
//
//
//        if(allMovieBySearchName.size() == 0){
//            List<Movie> allMovieBySearchDirectorOrActor = movieService.searchMovieByDirectorOrActor(name2);
//            if(allMovieBySearchDirectorOrActor.size() == 0){
//                res.put("code", 100);
//                res.put("msg", "false");
//            }
//            else{
//                int user_tag;
//                if(tagService.searchIdByTag(name2) == 0){
//                    tagService.insertTag(name2);
//                }
//                user_tag = tagService.searchIdByTag(name2);
//                if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
//                    userService.InsertUserTag(user_id, user_tag, 2);
//                }
//                else{
//                    userService.updateUserWeightByIdAndTag(user_id, user_tag, 2);
//                }
//                for (int i = 0; i < allMovieBySearchDirectorOrActor.size(); i++) {
//                    ja.add(JSONObject.fromObject(allMovieBySearchDirectorOrActor.get(i)));
//                }
//                res.put("data", ja.toString());
//                res.put("code", 200);
//                res.put("msg", "directorOrActor");
//            }
//        }
//        else{
//            for(int i = 0;i < allMovieBySearchName.size(); i++){
//                String[] intCateGory = allMovieBySearchName.get(i).getCategory().split("\\|");
//                for(int j = 1;j < intCateGory.length; j++){
//                    if(userService.ifNullFindByIdAndTag(user_id, Integer.parseInt(intCateGory[j])) == null){
//                        userService.InsertUserTag(user_id, Integer.parseInt(intCateGory[j]), 1);
//                    }
//                    else{
//                        userService.updateUserWeightByIdAndTag(user_id, Integer.parseInt(intCateGory[j]), 1);
//                    }
//                }
//                String firstActor = allMovieBySearchName.get(i).getActor().split("/")[0];
//                int user_tag;
//                if(tagService.searchIdByTag(firstActor) == 0){
//                    tagService.insertTag(firstActor);
//                }
//                user_tag = tagService.searchIdByTag(firstActor);
//                if(userService.ifNullFindByIdAndTag(user_id, user_tag) == null){
//                    userService.InsertUserTag(user_id, user_tag, 1);
//                }
//                else{
//                    userService.updateUserWeightByIdAndTag(user_id, user_tag, 1);
//                }
//            }
//            for (int i = 0; i < allMovieBySearchName.size(); i++) {
//                ja.add(JSONObject.fromObject(allMovieBySearchName.get(i)));
//            }
//            res.put("data", ja.toString());
//            res.put("code", 201);
//            res.put("msg", "movieName");
//        }

        //



//        movieService.deleteAllFromMoviePython();
//        userService.deleteAllFromUserPython();
//
//
//        String arg = "--user-id " + String.valueOf(user_id);
//        String[] args2 = new String[]{"python", "main.py", arg};
//        Process proc2 = Runtime.getRuntime().exec(args2);
//
//        Thread.sleep(20000);
//
//        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
//        List<Integer> recommendByUser = userService.getAllFromUserPython();
//
//        for(int i = 0;i < 10;i++){
//            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
//        }
//
//        for(int i = 0;i < 10;i++){
//            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
//        }


        response.getWriter().write(res.toString());




        //
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
//        System.out.println(ja.get(0).getClass());
        res.put("data", ja.toString());
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @PostMapping("/showAugustMovieDetail")
    public void showAugustMovieDetail(@RequestBody Movie m, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
//        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        Movie movie = movieService.searchAugustMovie(m.getMovieId());
        int user_id = m.getUserId();
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length - 1];
        for(int i = 1;i < intCateGory.length; i++){
            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result=String.join("，", stringCategory);
        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        JSONObject jo = new JSONObject();
        jo.put("detail", ja);


        //


        movieService.deleteAllFromMoviePython();
        userService.deleteAllFromUserPython();


//        String arg = "--user-id " + String.valueOf(user_id) + "--movie-id " +String.valueOf(m.getMovieId());
//        String[] args1 = new String[]{"python", "main.py", arg};
//        Process proc = Runtime.getRuntime().exec(args1);

        String arg = "python E:\\Study\\intern\\main.py --user-id " + String.valueOf(user_id) + " --movie-id " +String.valueOf(m.getMovieId());
        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(5000);

        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
        List<Integer> recommendByUser = userService.getAllFromUserPython();
        for(int i = 0;i < 10;i++){
            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
        }

        JSONArray ja2 = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
        }
        jo.put("recommendByMovie", ja);
        jo.put("recommendByUser", ja2);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());





        //
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
        res.put("code", 200);
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
        res.put("code", 200);
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
        res.put("code", 200);
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
        res.put("code", 200);
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
        res.put("code", 200);
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
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

//    @RequestMapping("/index")
//    public Object index(){
//        Movie movie = movieService.searchMovie(3445906);
////        Page<Movie> page = new Page<>(1,2);
//        return movie;
//    }

}
