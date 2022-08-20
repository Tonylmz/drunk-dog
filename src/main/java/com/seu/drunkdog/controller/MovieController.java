package com.seu.drunkdog.controller;

import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.service.MovieService;
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
    @RequestMapping("/getTopMovie")
    public void getTopMovie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession hs=request.getSession();
        response.setCharacterEncoding("UTF-8");
        List<Movie> allMovie = movieService.getAllMovie();
        JSONArray ja = new JSONArray();
        for (int i = 0; i < allMovie.size(); i++) {
            ja.add(JSONObject.fromObject(allMovie.get(i).getAll()));
        }
        JSONObject res = new JSONObject();
        res.put("msg", ja.toString());
        response.getWriter().write(res.toString());
    }

}
