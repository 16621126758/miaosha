package com.hand.miaosha.redis;

/**
 * @Class: Prefix
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 17:34
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
