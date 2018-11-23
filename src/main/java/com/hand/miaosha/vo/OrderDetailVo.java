package com.hand.miaosha.vo;

import com.hand.miaosha.domain.OrderInfo;

/**
 * @Class: OrderDetailVo
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-20 16:42
 */
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo orderInfo;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
