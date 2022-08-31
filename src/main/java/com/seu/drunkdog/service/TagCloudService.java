package com.seu.drunkdog.service;

import com.seu.drunkdog.mapper.TagCloudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class TagCloudService {
    @Autowired
    TagCloudMapper tagCloudMapper;
    public String searchTagByMovieId(int movie_id){ return tagCloudMapper.getTagCloud(movie_id); }
}
