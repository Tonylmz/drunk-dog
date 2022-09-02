package com.seu.drunkdog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seu.drunkdog.entity.*;
import com.seu.drunkdog.service.MovieService;
import com.seu.drunkdog.service.TagCloudService;
import com.seu.drunkdog.service.TagService;
import com.seu.drunkdog.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
    @Autowired
    TagCloudService tagCloudService;

    @RequestMapping("/getTopMovie")
    public void getTopMovie(@RequestBody Search search, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        int pageNo = search.getPageNo();
        Page<Movie> page = new Page<>(pageNo,12);
        IPage<Movie> iPage = movieService.searchAllTopMovie(page);
        JSONObject res = new JSONObject();
        res.put("data", iPage);
        res.put("msg", "true");
        res.put("code", 200);
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showTopMovie")
    public void showTopMovie(@RequestBody Movie m, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        Movie movie = movieService.searchTopMovie(m.getMovieId());
        TagCloud tagCloud = new TagCloud();
        tagCloud.setMovieTag(tagCloudService.searchTagByMovieId(m.getMovieId()));
        movie.setTagCloud(tagCloud);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        JSONObject jo = new JSONObject();
        jo.put("detail", ja);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showTopMovieRecommend")
    public void showTopMovieRecommend(@RequestBody Movie m, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int user_id = m.getUserId();
        JSONObject res = new JSONObject();
        movieService.deleteAllFromMoviePython();
        userService.deleteAllFromUnitePython();
        String arg = "python main.py --user-id " + String.valueOf(user_id) + " --movie-id " +String.valueOf(m.getMovieId());
        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(2500);
        List<Integer> recommendByUnite = userService.getAllFromUnitePython();
        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
        JSONArray ja1 = new JSONArray();
        JSONArray ja2 = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
        }
        for(int i = 0;i < 10;i++){
            ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUnite.get(i))));
        }
        JSONObject jo = new JSONObject();
        jo.put("recommendByMovie", ja1);
        jo.put("recommendByUnite", ja2);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/showMovie")
    public void showMovie(@RequestBody Movie m, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONObject jo = new JSONObject();
        int movie_id = m.getMovieId();
        Movie movie = movieService.searchMovie(movie_id);
        TagCloud tagCloud = new TagCloud();
        tagCloud.setMovieTag(tagCloudService.searchTagByMovieId(m.getMovieId()));
        movie.setTagCloud(tagCloud);
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length - 1];
        for(int i = 1;i < intCateGory.length; i++){
            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result=String.join(" ", stringCategory);
        movie.setCategory(result);
        jo.put("detail", movie);
        JSONObject res = new JSONObject();
        res.put("data", jo.toString());
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showMovieRecommend")
    public void showMovieRecommend(@RequestBody Movie m, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONObject jo = new JSONObject();
        int movie_id = m.getMovieId();
        int user_id = m.getUserId();
        JSONObject res = new JSONObject();
        movieService.deleteAllFromMoviePython();
        userService.deleteAllFromUnitePython();
        String arg = "python main.py --user-id " + String.valueOf(user_id) + " --movie-id " +String.valueOf(movie_id);
        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(2500);
        List<Integer> recommendByUnite = userService.getAllFromUnitePython();
        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
        JSONArray ja1 = new JSONArray();
        JSONArray ja2 = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
        }
        for(int i = 0;i < 10;i++){
            ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUnite.get(i))));
        }
        jo.put("recommendByMovie", ja1);
        jo.put("recommendByUnite", ja2);
        res.put("data", jo.toString());
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/movieCommentByUser")
    public void movieCommentByUser(@RequestBody MovieComment movieComment, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = movieComment.getMovieId();
        String movie_comment = movieComment.getMovieComment();
        int maxScore = movieService.getMaxScore(movie_id);
        movieService.insertMovieComment(movie_id, movie_comment, maxScore + 1);
        Page<MovieComment> page = new Page<>(1, 10);
        IPage<MovieComment> iPage = movieService.searchMovieCommentByIdAndPage(movie_id, page);
        JSONObject res = new JSONObject();
        res.put("data", iPage);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/getMovieComment")
    public void getMovieComment(@RequestBody MovieComment movieComment, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = movieComment.getMovieId();
        int pageNo = movieComment.getPageNo();
        Page<MovieComment> page = new Page<>(pageNo, 10);
        IPage<MovieComment> iPage = movieService.searchMovieCommentByIdAndPage(movie_id, page);
        JSONObject res = new JSONObject();
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
        Movie movie = movieService.searchMovie(movie_id);
        String[] intCateGory = movie.getCategory().split("\\|");
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
        String arg = "python main.py --user-id " + String.valueOf(user_id);
        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(2500);
        List<Integer> recommendByUser = userService.getAllFromUserPython();
        JSONArray ja = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByUser.get(i))));
        }
        JSONObject jo = new JSONObject();
        jo.put("recommendByUser", ja);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/clickLike")
    public void clickLike(@RequestBody Movie movie, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        movieService.addMovieLike(movie.getId());
        JSONObject res = new JSONObject();
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showMovieBySearch")
    public void showMovieBySearch(@RequestBody Search search, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        //此处需要python函数分词
        String name = "--text " + search.getName();
        int user_id = search.getUserId();
        String args1 = "python hmm.py " + name;
        movieService.deleteAllResult();
        Process proc = Runtime.getRuntime().exec(args1);
        Thread.sleep(4000);
        List<HmmResult> out = movieService.findResult();
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
        if(k == -1){
            for(int i = 0;i < out.size();i++){
                List<Movie> allMovieBySearchDirectorOrActor = movieService.searchMovieByDirectorOrActor(out.get(i).getResult());
                if(allMovieBySearchDirectorOrActor.size()!= 0){
                    k = i;
                    break;
                }
            }
            if(k == -1){
                for(int i = 0;i < out.size();i++){
                    int tag = tagService.searchIdByTag(out.get(i).getResult());
                    if(tag != 0){
                        k = i;
                        break;
                    }
                }
                if(k == -1){
                    res.put("code", 100);
                    res.put("msg", "false");
                }
                else{
                    int tag = tagService.searchIdByTag(out.get(k).getResult());
                    Page<Movie> page = new Page<>(1,12);
                    IPage<Movie> iPage = movieService.getMovieByCategoryAndPage(page, tag);
                    res.put("data", iPage);
                    res.put("code", 202);
                    res.put("name", out.get(k).getResult());
                    res.put("msg", "movieTag");
                }
            }
            else{
                String name2 = out.get(k).getResult();
                List<Movie> allMovieBySearchDirectorOrActor = movieService.searchMovieByDirectorOrActor(name2);
//                }
                for (int i = 0; i < allMovieBySearchDirectorOrActor.size(); i++) {
                    ja.add(JSONObject.fromObject(allMovieBySearchDirectorOrActor.get(i)));
                }

                JSONObject jo1 = new JSONObject();
                jo1.put("detail", ja);
                res.put("data", jo1);
                res.put("code", 200);
                res.put("name", out.get(k).getResult());
                res.put("msg", "directorOrActor");
            }
        }
        else{
            List<Movie> allMovieBySearchName = movieService.searchMovieByName(out.get(k).getResult());
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
            }
            for (int i = 0; i < allMovieBySearchName.size(); i++) {
                ja.add(JSONObject.fromObject(allMovieBySearchName.get(i)));
            }
            JSONObject jo = new JSONObject();
            jo.put("detail", ja);
            res.put("data", jo);
            res.put("code", 201);
            res.put("name", out.get(k).getResult());
            res.put("msg", "movieName");
        }
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showMovieBySearchRecommend")
    public void showMovieBySearchRecommend(@RequestBody Search search, HttpServletResponse response) throws Exception {
        String name = search.getName();
        int user_id = search.getUserId();
        String msg = search.getMsg();
        JSONObject res = new JSONObject();
        if(msg.equals("movieName")){
            List<Movie> allMovieBySearchName = movieService.searchMovieByName(name);
            movieService.deleteAllFromMoviePython();
            userService.deleteAllFromUnitePython();
            String arg = "python main.py --user-id " + String.valueOf(user_id) + " --movie-id " + String.valueOf(allMovieBySearchName.get(0).getMovieId());
            Process proc2 = Runtime.getRuntime().exec(arg);
            Thread.sleep(2500);
            List<Integer> recommendByUnite = userService.getAllFromUnitePython();
            List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
            JSONArray ja1 = new JSONArray();
            for(int i = 0;i < 10;i++){
                ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
            }
            JSONArray ja2 = new JSONArray();
            for(int i = 0;i < 10;i++){
                ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUnite.get(i))));
            }
            JSONObject jo = new JSONObject();
            jo.put("recommendByMovie", ja1);
            jo.put("recommendByUnite", ja2);
            res.put("data", jo);
            res.put("code", 201);
            res.put("msg", "movieName");
        }
        else if(msg.equals("directorOrActor")){
            List<Movie> allMovieBySearchDirectorOrActor = movieService.searchMovieByDirectorOrActor(name);
            movieService.deleteAllFromMoviePython();
            userService.deleteAllFromUnitePython();
            String arg = "python main.py --movie-id " + String.valueOf(allMovieBySearchDirectorOrActor.get(0).getMovieId()) + "--user-id" + String.valueOf(user_id);
            Process proc2 = Runtime.getRuntime().exec(arg);

            Thread.sleep(2500);

            List<Integer> recommendByUnite = userService.getAllFromUnitePython();
            List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
            JSONArray ja1 = new JSONArray();
            for(int i = 0;i < 10;i++){
                ja1.add(JSONObject.fromObject(movieService.searchMovie(recommendByUnite.get(i))));
            }
            JSONArray ja2 = new JSONArray();
            for(int i = 0;i < 10;i++){
                ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
            }
            JSONObject jo1 = new JSONObject();
            jo1.put("recommendByUnite", ja1);
            jo1.put("recommendByMovie", ja2);
            res.put("data", jo1);
            res.put("code", 200);
            res.put("msg", "directorOrActor");
        }
        else{
            int pageNo = search.getPageNo();
            int tag = tagService.searchIdByTag(name);
            Page<Movie> page = new Page<>(pageNo,12);
            IPage<Movie> iPage = movieService.getMovieByCategoryAndPage(page, tag);
            res.put("data", iPage);
            res.put("code", 202);
            res.put("msg", "movieTag");
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
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/showAugustMovieDetail")
    public void showAugustMovieDetail(@RequestBody Movie m, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        Movie movie = movieService.searchAugustMovie(m.getMovieId());
        TagCloud tagCloud = new TagCloud();
        tagCloud.setMovieTag(tagCloudService.searchTagByMovieId(m.getMovieId()));
        movie.setTagCloud(tagCloud);
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length - 1];
        for(int i = 1;i < intCateGory.length; i++){
            stringCategory[i - 1] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result = String.join(" ", stringCategory);
        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        JSONObject jo = new JSONObject();
        jo.put("detail", ja);
        res.put("data", jo);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showAugustMovieDetailRecommend")
    public void showAugustMovieDetailRecommend(@RequestBody Movie m, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        int user_id = m.getUserId();
        JSONObject res = new JSONObject();
        JSONObject jo = new JSONObject();
        movieService.deleteAllFromMoviePython();
        userService.deleteAllFromUnitePython();
        String arg = "python main.py --user-id " + String.valueOf(user_id) + " --movie-id " +String.valueOf(m.getMovieId());
        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(2500);
        List<Integer> recommendByMovie = movieService.getAllFromMoviePython();
        List<Integer> recommendByUnite = userService.getAllFromUnitePython();
        for(int i = 0;i < 10;i++){
            ja.add(JSONObject.fromObject(movieService.searchMovie(recommendByMovie.get(i))));
        }
        JSONArray ja2 = new JSONArray();
        for(int i = 0;i < 10;i++){
            ja2.add(JSONObject.fromObject(movieService.searchMovie(recommendByUnite.get(i))));
        }
        jo.put("recommendByMovie", ja);
        jo.put("recommendByUnite", ja2);
        res.put("data", jo);
        res.put("code", 200);
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
        ja2[4].put("日韩", movieService.numOfJapanOrKorea());
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
    @RequestMapping("/regionBoxJapanOrKorea")
    public void regionBoxJapanOrKorea(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        List<Movie> top5MovieJapanOrKorea = movieService.getMovieJapanOrKorea();
        for (int i = 0; i < 5; i++) {
            ja.add(JSONObject.fromObject(top5MovieJapanOrKorea.get(i)));
        }
        JSONObject res = new JSONObject();
        res.put("data", ja.toString());
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/AQS")
    public void AQS(@RequestBody Search search, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("utf-8");
        String message = search.getMsg();
        String arg = "python IntelligentCommunicationSystem.py --text " + message;
        Process proc = Runtime.getRuntime().exec(arg);
        Thread.sleep(2500);
        String answer = movieService.getAnswer();
        JSONObject res = new JSONObject();
        res.put("data", answer);
        res.put("code", 200);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }

}
