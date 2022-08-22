package com.seu.drunkdog.service;

import com.seu.drunkdog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class TagService {
    @Autowired
    TagMapper tagMapper;
    public void insertTag(String category){tagMapper.saveCategory(category);}
    public int searchIdByTag(String category){return tagMapper.getIdByCategory(category);}

    public String searchTagById(int id){ return tagMapper.getCategoryById(id); }

}