package com.hand.miaosha.controller;

import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.result.CodeMsg;
import com.hand.miaosha.result.Result;
import com.hand.miaosha.service.GoodsService;
import com.hand.miaosha.service.OrderService;
import com.hand.miaosha.vo.GoodsVo;
import com.hand.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Class: OrderController
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-20 16:40
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;



    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(MiaoshaUser user,@RequestParam("orderId") long orderId ){
        if (user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order =  orderService.getOrderById(orderId);
        System.out.println("查询到的order为==================="+order);

        if (null==order){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = order.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        System.out.println("查询到的goodVo为================"+goods);

        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoods(goods);
        orderDetailVo.setOrderInfo(order);
        return Result.success(orderDetailVo);

    }

}
