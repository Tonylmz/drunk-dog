package com.seu.drunkdog.service;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.entity.UserTag;
import com.seu.drunkdog.mapper.UserMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @RequestMapping("/mybatisFindById")
//    public User mybatisFindById(int id){
//        return userMapper.selectByUserId(id);
//    }
//    @RequestMapping("/insertUser")
//    public String insertUser(String name, String password){
//        int update = jdbcTemplate.update("insert into user values (null, ?, ?)", name, password);
//        return update > 0?"success":"fail";
//    }
    public User LoginIn(String name){
        return userMapper.getInfo(name);
    }

    public void InsertUser(String name, String password){
        userMapper.saveInfo(name, password);
    }

    public void getAllById(int user_id){ userMapper.findAllById(user_id); }

    public void InsertUserTag(UserTag userTag){ userMapper.saveUserTag(userTag); }
}
