package com.hand.miaosha.controller;

import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.User;
import com.hand.miaosha.redis.GoodsKey;
import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.service.GoodsService;
import com.hand.miaosha.service.Impl.MiaoshaUserServiceImpl;

import com.hand.miaosha.service.MiaoshaUserService;
import com.hand.miaosha.vo.GoodsData;
import com.hand.miaosha.vo.GoodsVo;
import io.lettuce.core.ScriptOutputType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.List;

/**
 * @Class: GoodsController
 * @description:进行了优化
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 17:22
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    //springboot自己带的，渲染页面用的
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletResponse response,HttpServletRequest request,
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
        //取页面缓存
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if (StringUtils.isNotEmpty(html)){
            return html;
        }
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        System.out.println(goodsList.get(0));
        model.addAttribute("goodsList",goodsList);
       // return "goods_list";

       //手动渲染页面
        //springboot1.x 可以用
//        SpringWebContext springWebContext = new SpringWebContext(request,response,request.getServletContext(),
//                request.getLocale(),model.asMap());
        //springboot 1.x以后
        WebContext context = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",context);
        if (StringUtils.isNotEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
     }

    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletResponse response,HttpServletRequest request,
                         Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId ){
        System.out.println("进入 详情页----------------");
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+goodsId);

        model.addAttribute("user",user);
        //snowflake算法   一般id不用自增

        //取页面缓存
        String html = redisService.get(GoodsKey.getGoodsDetail,""+goodsId,String.class);
        if (StringUtils.isNotEmpty(html)){
            return html;
        }
      GoodsVo goods =  goodsService.getGoodsVoByGoodsId(goodsId);
       model.addAttribute("goods",goods);

       long startAt = goods.getStartDate().getTime();
       long endAt = goods.getEndDate().getTime();
       long now = System.currentTimeMillis();
       int miaoshaStatus = 0;
       int remainSeconds = 0;

       if (now<startAt){    //秒杀还没有开始，倒计时
           miaoshaStatus = 0;
           remainSeconds = (int)((startAt-now)/1000);
       } else if (endAt<now){//秒杀已经结束
           miaoshaStatus = 2;
           remainSeconds = -1;
       }
       else { //秒杀进行中
           miaoshaStatus = 1;
           remainSeconds = 0;
           }

       model.addAttribute("miaoshaStatus",miaoshaStatus);
       model.addAttribute("remainSeconds",remainSeconds);
       //return "goods_detail";
        WebContext context = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",context);
        if (StringUtils.isNotEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);
        }
        return html;
    }
}
