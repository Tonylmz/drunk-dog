package com.seu.drunkdog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.service.MovieService;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Service
public class MovieMapperPage {
    @Autowired
    MovieService movieService;
    @RequestMapping("/selectPage")
    public void selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        LambdaQueryWrapper<Movie> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        response.setCharacterEncoding("UTF-8");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Page<Movie> page = new Page<>(pageNo,10);
        IPage<Movie> iPage = movieService.getMovieByPage(page);
        JSONObject res = new JSONObject();
        res.put("data", iPage);
        res.put("code", 0);
        res.put("msg", "true");
        response.getWriter().write(res.toString());
    }
}
