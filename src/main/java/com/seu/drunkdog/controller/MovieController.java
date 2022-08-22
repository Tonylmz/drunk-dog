package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.service.MovieService;
import com.seu.drunkdog.service.TagService;
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

@Service
@RestController
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    TagService tagService;
    @RequestMapping("/getTopMovie")
    public void getTopMovie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
        List<Movie> allMovie = movieService.searchAllMovie();
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovie.size(); i++) {
            ja.add(JSONObject.fromObject(allMovie.get(i).getAll()));
        }
        JSONObject res = new JSONObject();
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/showTopMovie")
    public void showTopMovie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs = request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        int id = Integer.parseInt(request.getParameter("id"));
        Movie movie = movieService.searchTopMovie(id);
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length];
        for(int i = 0;i < intCateGory.length; i++){
            stringCategory[i] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
//        Cookie c = new Cookie("name", movie.getName());
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(c);
//        Cookie d = new Cookie("introduction", movie.getIntroduction());
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(d);
//        Cookie e = new Cookie("director", movie.getDirector());
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(e);
//        Cookie f = new Cookie("actor", movie.getActor());
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(f);
//        Cookie g = new Cookie("score", String.valueOf(movie.getScore()));
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(g);
//        Cookie h = new Cookie("picture_link", movie.getPicture_link());
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(h);
//        Cookie i = new Cookie("release_period", movie.getRelease_period());
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(i);
//        Cookie j = new Cookie("stringCategory", stringCategory);
//        c.setMaxAge(10800);
//        c.setPath("/");
//        response.addCookie(j);
        String result=String.join("|", stringCategory);
        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());

    }
    @RequestMapping("/showMovie")
    public void showMovie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray();
        int id = Integer.parseInt(request.getParameter("id"));
        Movie movie = movieService.searchMovie(id);
        String[] intCateGory = movie.getCategory().split("\\|");
        String[] stringCategory = new String[intCateGory.length];
        for(int i = 0;i < intCateGory.length; i++){
            stringCategory[i] = tagService.searchTagById(Integer.parseInt(intCateGory[i]));
        }
        String result=String.join("|", stringCategory);
        movie.setCategory(result);
        ja.add(JSONObject.fromObject(movie));
        JSONObject res = new JSONObject();
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());
    }
    @RequestMapping("/MovieCommentByUser")
    public void MovieCommentByUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        String movie_comment = request.getParameter("movie_comment");
        movieService.insertMovieComment(movie_id, movie_comment);

    }
    @RequestMapping("/GetMovieComment")
    public void GetMovieComment(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        int movie_id = Integer.parseInt(request.getParameter("movie_id"));
        List<String> allMovieCommentById = movieService.searchMovieCommentById(movie_id);
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovieCommentById.size(); i++) {
            ja.add(allMovieCommentById.get(i));
        }
        JSONObject res = new JSONObject();
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());
    }

    @RequestMapping("/showMovieBySearch")
    public void showMovieBySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        List<Movie> allMovieBySearch = movieService.searchMovieByNameOrDirectorOrActor(name);
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovieBySearch.size(); i++) {
            ja.add(JSONObject.fromObject(allMovieBySearch.get(i).getAll()));
        }
        JSONObject res = new JSONObject();
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());
    }


//    @RequestMapping("/GetMovieComment2")
//    public void GetMovieComment2() throws Exception{
//        int movie_id = 1;
//        List<String> allMovieCommentById = movieService.searchMovieCommentById(movie_id);
//        for (int i = 0; i < allMovieCommentById.size(); i++) {
//            System.out.println(allMovieCommentById.get(i));
//        }
//    }
}
