package com.hand.miaosha.redis;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;


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
     * @param
     * @param key
     * @param
     * @return
     */
/*    public boolean delete(KeyPrefix keyprefix,String key){
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
    }*/

    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            long ret =  jedis.del(realKey);
            return ret > 0;
        }finally {
            returnToPoll(jedis);
        }
    }
    public boolean delete(KeyPrefix prefix) {
        if(prefix == null) {
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        if(keys==null || keys.size() <= 0) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<String>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*"+key+"*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result!=null && result.size() > 0){
                    keys.addAll(result);
                }
                //再处理cursor
                cursor = ret.getStringCursor();
            }while(!cursor.equals("0"));
            return keys;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
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
            T t = stringToBean(str,clazz);
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

    public static <T> String beanToString(T value) {
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

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }


    private void returnToPoll(Jedis jedis) {
        if (jedis!=null){
                    jedis.close();
        }

    }



}
