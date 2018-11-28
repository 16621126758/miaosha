package com.hand.miaosha.redis;

/**
 * @Class: AccessKey
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-23 11:05
 */
public class AccessKey extends BasePrefix{
    public AccessKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static AccessKey access = new AccessKey(5,"access");

    public static AccessKey withExpire(int expireSecond){
        return new AccessKey(expireSecond,"access");
    }



}
