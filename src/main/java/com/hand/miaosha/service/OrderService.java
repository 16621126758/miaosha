package com.hand.miaosha.service;

import com.hand.miaosha.domain.MiaoshaOrder;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.vo.GoodsVo;

/**
 * @Class: OrderService
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-16 09:18
 */
public interface OrderService {
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long id, long goodsId);

    OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);
}
