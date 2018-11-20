package com.hand.miaosha.redis;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.swing.text.StyledEditorKit;


/**
 * @Class: RedisService
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 10:00
 */
@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     *
     * @param keyprefix
     * @param key
     * @param <删除
     * @return
     */
    public boolean delete(KeyPrefix keyprefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyprefix.getPrefix()+key;
            long ret =  jedis.del(realKey);
            return ret>0;
        }finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 获取单个对象
     * @param keyprefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public<T> T get(KeyPrefix keyprefix,String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyprefix.getPrefix()+key;
            String str = jedis.get(realKey);
            T t = StringToBean(str,clazz);
            return t;
        }finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 这只对象
     * @param keyprefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public<T> boolean set(KeyPrefix keyprefix,String key, T value ){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String str = beanToString(value);
            if (null == str||str.length()<=0){
                return false;
            }
            //生成正真的key
            String realKey = keyprefix.getPrefix()+key;
            int seconds = keyprefix.expireSeconds();
            if (seconds<=0){
                jedis.set(realKey,str);
            }else {
                jedis.setex(realKey,seconds,str);
            }

            return true;
        }finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param keyprefix
     * @param key
     * @param <T>
     * @return
     */
    public<T> boolean exists(KeyPrefix keyprefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyprefix.getPrefix()+key;
            return jedis.exists(realKey);
        }finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 增加值
     * @param keyprefix
     * @param key
     * @param <T>
     * @return
     */
    public<T> Long incr(KeyPrefix keyprefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyprefix.getPrefix()+key;
            return jedis.incr(realKey);
        }finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 减少值
     * @param keyprefix
     * @param key
     * @param <T>
     * @return
     */
    public<T> Long decr(KeyPrefix keyprefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyprefix.getPrefix()+key;
            return jedis.decr(realKey);
        }finally {
            returnToPoll(jedis);
        }
    }

    private<T> String beanToString(T value) {
        if (value == null){
            return null;
        }
        Class<?> clazz =value.getClass();
        if (clazz == int.class||clazz == Integer.class){
            return ""+value;
        } else if (clazz == String.class){
            return (String)value;
        } else if (clazz == long.class||clazz == Long.class){
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T StringToBean(String str,Class<T> clazz) {
        if (StringUtils.isEmpty("str")||null==clazz){
            return null;
        }
        if (clazz == int.class||clazz == Integer.class){
            return (T)Integer.valueOf(str);
        } else if (clazz == String.class){
            return (T)str;
        } else if (clazz == long.class||clazz == Long.class){
            return (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }



    }

    private void returnToPoll(Jedis jedis) {
        if (jedis!=null){
                    jedis.close();
        }

    }



}
