package com.hand.miaosha.redis;

/**
 * @Class: GoodsKey
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-19 18:56
 */
public class GoodsKey extends BasePrefix {
    public GoodsKey(int expireSeconds,String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
}
