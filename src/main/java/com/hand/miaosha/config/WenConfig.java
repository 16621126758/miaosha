package com.hand.miaosha.config;

import com.hand.miaosha.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    @Autowired
    private AccessInterceptor accessInterceptor;


    //注册拦截器,通过一下的代码就将拦截器注册到系统中来了
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
        super.addInterceptors(registry);
    }

    //这个是往controller中赋值用的，比如httpresponse等
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
       // super.addArgumentResolvers(argumentResolvers);
    }
}

