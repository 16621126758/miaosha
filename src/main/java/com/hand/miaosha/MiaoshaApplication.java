package com.hand.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan({"com.hand.miaosha.dao","com.hand.miaosha.redis"})
@Component("com.hand.miaosha.redis")
public class MiaoshaApplication //extends SpringBootServletInitializer
{


    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }


//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return super.configure(builder);
//    }
}
