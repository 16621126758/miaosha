package com.hand.miaosha.vo;

import com.hand.miaosha.domain.MiaoshaUser;

/**
 * @Class: GoodsDetailVo
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-20 10:10
 */
public class GoodsDetailVo {

    private int miaoshaStatus ;
    private int remainSeconds ;
    private GoodsVo goods;
    private MiaoshaUser miaoshaUser;

    public MiaoshaUser getMiaoshaUser() {
        return miaoshaUser;
    }

    public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        this.miaoshaUser = miaoshaUser;
    }

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
}
