package com.hand.miaosha.redis;

/**
 * @Class: OrderKey
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 17:45
 */
public class OrderKey extends BasePrefix {
    public OrderKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }
}
