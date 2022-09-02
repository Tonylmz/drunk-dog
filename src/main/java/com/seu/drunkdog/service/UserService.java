package com.seu.drunkdog.service;

import com.seu.drunkdog.entity.Movie;
import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.entity.UserTag;
import com.seu.drunkdog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User LoginIn(String name){
        return userMapper.getInfo(name);
    }
    public User searchUserById(int id){
        return userMapper.getInfoById(id);
    }

    public void InsertUser(String name, String password){
        userMapper.saveInfo(name, password);
    }

    public List<UserTag> searchAllById(int user_id){ return userMapper.findAllById(user_id); }

    public void InsertUserTag(int user_id, int user_tag, double user_weight){
        userMapper.saveUserTag(user_id, user_tag, user_weight);
    }

    public int searchIdByTag(String category){ return userMapper.getIdByTag(category); }


    public List<Movie> searchAllMovieByTag(int tag){ return userMapper.getAllMovieByTag(tag); }

    public User Sel(int id){ return userMapper.sel(id); }

    public UserTag ifNullFindByIdAndTag(int user_id, int user_tag){ return userMapper.findByIdAndTag(user_id, user_tag); }

    public void updateUserWeightByIdAndTag(int user_id, int user_tag, double user_weight){ userMapper.updateUserWeightByIdAndTag(user_id, user_tag, user_weight);}

    public void deleteAllFromUserPython(){ userMapper.deleteFromUserPython();}

    public List<Integer> getAllFromUserPython(){ return userMapper.getUserPython();}

    public void deleteAllFromUnitePython(){ userMapper.deleteFromUnitePython(); }

    public List<Integer> getAllFromUnitePython(){ return userMapper.getUnitePython(); }

    public int judgeIfFirstLogin(String name, String password){ return userMapper.ifFirstLogin(name, password); }

    public void updateUserIfFirstLogin(String name, String password){ userMapper.updateIfFirstLogin(name, password);}
}
