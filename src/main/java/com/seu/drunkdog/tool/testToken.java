package com.seu.drunkdog.tool;

import com.seu.drunkdog.entity.User;
import com.seu.drunkdog.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.seu.drunkdog.tool.createToken;
import org.thymeleaf.util.TextUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
@RestController
@Service
public class testToken {
    @Autowired
    UserService userService;
//    @RequestMapping("/tokensign")
    public Object tokenSign(String token){
//        System.out.println("token ------  >   " + token );
        Map<String,Object> map=new HashMap<>();
        if(token.equals("")){
            map.put("msg", "请求参数错误或者请求参数缺失");
            map.put("code", 101);
//            return false;
        }else {
            try{
                Claims claims = Jwts.parser()
                        .setSigningKey("my-123")
                        .parseClaimsJws(token)
                        .getBody();
                Integer id=Integer.valueOf(claims.getId());
//                System.out.println("用户id:"+claims.getId());
//                System.out.println("用户名:"+claims.getSubject());
//                System.out.println("用户时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
//                        format(claims.getIssuedAt()));
//                System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
//                        format(claims.getExpiration()));
                String username = claims.getSubject();
//                System.out.println(id);
//                System.out.println(username);
                User user = userService.searchUserById(id);
//                System.out.println(user.getId());
//                System.out.println(user.getName());
                if(username!=null&&claims.getId()!=null&&username.equals(user.getName())){
                    String gettoken=createToken.getToken(user);
                    map.put("userId",user.getId());
//                    map.put("token",gettoken);
                    map.put("msg", "成功");
                    map.put("code", 200);
//                    return true;
                }else {
                    map.put("msg", "token错误");
                    map.put("code", 100);
//                    return false;
                }
            }catch (Exception E){
                map.put("msg", "token格式正确");
                map.put("code", 102);

            }
        }
//        return false;
        return map;
    }

}
