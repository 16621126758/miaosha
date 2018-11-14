package com.hand.miaosha.controller;



import com.hand.miaosha.result.Result;
import com.hand.miaosha.service.MiaoshaUserService;
import com.hand.miaosha.vo.LoginVo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Class: LoginController
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-09 09:29
 */
@Controller
@RequestMapping("login")
public class LoginController {

    private  static org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        //参数校验
//        String passinput = loginVo.getPassword();
//        String mobile=loginVo.getMobile();
//        if (StringUtils.isEmpty(passinput)){
//            return  Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if (StringUtils.isEmpty(passinput)){
//            return  Result.error(CodeMsg.MOBILE_EMPTU);
//        }
        //登陆
         miaoshaUserService.login(response,loginVo);
       // System.out.println("返回的code为++++++++++++++++++"+mg.getCode());
//        if (mg.getCode()==0){
//           return Result.success(CodeMsg.SUCCESS);
//        }else {
//            return Result.error(mg);
//        }
       //return null;
        return Result.success(true);
    }
}
