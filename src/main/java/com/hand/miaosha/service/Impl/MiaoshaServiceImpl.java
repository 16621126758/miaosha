package com.hand.miaosha.service.Impl;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.service.GoodsService;
import com.hand.miaosha.service.MiaoshaService;
import com.hand.miaosha.service.OrderService;
import com.hand.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class: MiaoshaServiceImpl
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-16 09:27
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减少库存  下订单  写入秒杀订单
        goodsService.reduceStock(goods);
        //生成订单
        return orderService.createOrder(user,goods);
    }
}
