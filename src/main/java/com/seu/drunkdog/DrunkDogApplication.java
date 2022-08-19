package com.seu.drunkdog;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.seu.drunkdog.mapper")
public class DrunkDogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrunkDogApplication.class, args);
    }

}
