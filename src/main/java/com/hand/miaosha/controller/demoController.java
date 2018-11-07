package com.hand.miaosha.controller;

import com.hand.miaosha.dao.UserMapper;
import com.hand.miaosha.domain.User;
import com.hand.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Class: demoController
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-06 10:55
 */
@Controller
public class demoController {
    @Autowired
    private UserMapper userMapper;
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
    public Result<User> dbGet(){
        List<User> userNames = userMapper.query();
        User userName = userNames.get(0);
        return Result.success(userName);
    }
}
