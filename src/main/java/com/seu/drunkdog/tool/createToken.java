package com.seu.drunkdog.tool;

import com.seu.drunkdog.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@RestController
@Service
public class createToken {
    public static String getToken(User user) {

//        String s = String.valueOf(user.getId());
//        String token= JWT.create().withAudience(s)
//                .sign(Algorithm.HMAC256(user.getPassword()));
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getName())
                .setIssuedAt(new Date())//登录时间
                .signWith(SignatureAlgorithm.HS256, "my-123").setExpiration(new Date(new
                        Date().getTime()+1800000));
        return jwtBuilder.compact();
    }
//    public static boolean checkToken(String token){
//        if(token == null){
//            return false;
//        }
//        try {
//
//        }catch (Exception e){
//            return false;
//        }
//        return true;
//    }
}
