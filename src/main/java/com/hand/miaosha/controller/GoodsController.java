package com.hand.miaosha.controller;

import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.User;
import com.hand.miaosha.service.Impl.MiaoshaUserServiceImpl;

import com.hand.miaosha.service.MiaoshaUserService;
import io.lettuce.core.ScriptOutputType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @Class: GoodsController
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 17:22
 */
@Controller
@RequestMapping("/goods/")
public class GoodsController {
    @Autowired
    private MiaoshaUserService userService;

    @RequestMapping("/to_list")
    public String toList(
            //HttpServletResponse response,
            Model model,
//            , @CookieValue(value = MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN ,required = false) String cookieToken,
//                          //下面的是为了兼容把cookie直接放在参数中的情况
//                          @RequestParam(value =MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN,required = false)String paramToken
                         MiaoshaUser user){
        //接下来会判断优先级
//        System.out.println("进入到listcontrollerZH欧冠你 士大夫大师傅但是都是");
//        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user = userService.getByToken(response,token);
//        System.out.println(cookieToken);
        model.addAttribute("user",user);
       //model.addAttribute("user",new MiaoshaUser());
        return "goods_list";
    }
}
