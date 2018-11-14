package com.hand.miaosha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Class: WenConfig
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 20:50
 */

@Configuration
public class WenConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    private UserArgumentResolver userArgumentResolver;

    //这个是往controller中赋值用的，比如httpresponse等
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
       // super.addArgumentResolvers(argumentResolvers);
    }
}

