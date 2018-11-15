package com.hand.miaosha.controller;

import com.hand.miaosha.dao.UserMapper;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.User;
import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.redis.UserKey;
import com.hand.miaosha.result.Result;
import com.hand.miaosha.service.Impl.UserServiceImpl;
import com.hand.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.List;

/**
 * @Class: demoController
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-06 10:55
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    @Resource
    private UserService userService;
    @RequestMapping(value = "/hello")
    public Result<String> Hello(){
        return Result.success("hello");
    }

    @RequestMapping(value = "/thymeleaf")
    public String helloError(Model model){
        model.addAttribute("name","赵洪志");
        return "Hello";
    }

    @RequestMapping(value = "/db/get")
    @ResponseBody
    public Result<List<User>> dbGet(){
        List<User> userNames = userMapper.query();
        User userName = userNames.get(0);
        return Result.success(userNames);
    }

    @RequestMapping(value = "/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping(value = "/demo")
    @ResponseBody
    public Result<Integer> demo(){
         userService.toString();
         Integer i = userService.demo();

        return Result.success(i);
    }

    @RequestMapping(value = "/redis/get")
    @ResponseBody
    public Result<User> getredis(){
       User user =  redisService.get(UserKey.getById,""+1,User.class);
       return  Result.success(user);
    }

    @RequestMapping(value = "/redis/set")
    @ResponseBody
    public Result<Boolean> setredis(){
        User user = new User();
        user.setId(1);
        user.setName("毛表扬");
        redisService.set(UserKey.getById,""+1,user);
        return  Result.success(true);
    }


    //测试Jmeter
    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user){
        return Result.success(user);


    }


}
