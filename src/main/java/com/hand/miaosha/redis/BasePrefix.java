package com.hand.miaosha.redis;

import javax.xml.ws.Service;

/**
 * @Class: BasePrefix
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 17:36
 */
public abstract class  BasePrefix implements KeyPrefix{

    private int expireSeconds;

    private String prefix;

    //0默认代表永不过期
    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSecond, String prefix){
        this.expireSeconds=expireSecond;
        this.prefix=prefix;
    }

    @Override
    public int expireSeconds() { //默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}
