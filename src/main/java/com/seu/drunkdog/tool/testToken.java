package com.seu.drunkdog.tool;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
@RestController
@Service
public class testToken {
    @Autowired
    UserService userService;
    public Object tokenSign(String token){
        Map<String,Object> map=new HashMap<>();
        if(token.equals("")){
            map.put("msg", "请求参数错误或者请求参数缺失");
            map.put("code", 101);
        }else {
            try{
                Claims claims = Jwts.parser()
                        .setSigningKey("my-123")
                        .parseClaimsJws(token)
                        .getBody();
                Integer id=Integer.valueOf(claims.getId());
                String username = claims.getSubject();
                User user = userService.searchUserById(id);
                if(username!=null&&claims.getId()!=null&&username.equals(user.getName())){
                    map.put("userId",user.getId());
                    map.put("msg", "成功");
                    map.put("code", 200);
                }else {
                    map.put("msg", "token错误");
                    map.put("code", 100);
                }
            }catch (Exception E){
                map.put("msg", "token格式正确");
                map.put("code", 102);

            }
        }
        return map;
    }

}
