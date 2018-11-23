package com.hand.miaosha.rabbitmq;

import com.hand.miaosha.domain.MiaoshaUser;

/**
 * @Class: MiaoshaMessage
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-21 20:29
 */
public class MiaoshaMessage {

    private MiaoshaUser miaoshaUser;
    private long goodsId;

    public MiaoshaUser getMiaoshaUser() {
        return miaoshaUser;
    }

    public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        this.miaoshaUser = miaoshaUser;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
