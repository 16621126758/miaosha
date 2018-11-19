package com.hand.miaosha.controller;

import com.hand.miaosha.domain.MiaoshaOrder;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.result.CodeMsg;
import com.hand.miaosha.service.GoodsService;
import com.hand.miaosha.service.MiaoshaService;
import com.hand.miaosha.service.OrderService;
import com.hand.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Class: MiaoshaController
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-15 17:49
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("do_miaosha")
    public String list(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId){
        model.addAttribute("user",user);
        if (user == null){
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock<=0){
            model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER);
            return "miaosha_fail";
        }else{
            //判断是否已经秒杀过了
           MiaoshaOrder miaoshaOrder =  orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
           if (null!=miaoshaOrder){
               model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA);
               return "miaosha_fail";
           }
           //减少库存，下订单，写入秒杀订单
            OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
            model.addAttribute("orderInfo",orderInfo);
            System.out.println("orderinfo 的ID 为"+orderInfo.getId());
            model.addAttribute("goods",goods);
            return "order_detail";


        }
    }

}
