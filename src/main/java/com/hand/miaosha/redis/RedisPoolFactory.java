package com.hand.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Class: RedisPoolFactory
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 16:08
 */
@Service
public class RedisPoolFactory {
    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool jedisFaactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //System.out.println(redisConfig.getHost());

//        poolConfig.setMaxIdle(10);
//        poolConfig.setMaxTotal(8);
//        poolConfig.setMaxWaitMillis(3000);
//        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379, 2000,
//                null, 0);
        System.out.println("---------------------------getPoolMaxIdle"+redisConfig.getPoolMaxIdle());
        System.out.println("---------------------------getPoolMaxTotal"+redisConfig.getPoolMaxTotal());
        System.out.println("---------------------------getPoolMaxWait"+redisConfig.getPoolMaxWait()*1000);
        System.out.println("---------------------------getHost"+redisConfig.getHost());
        System.out.println("---------------------------getPort"+redisConfig.getPort());
        System.out.println("---------------------------getPassword"+redisConfig.getPassword());
        System.out.println("---------------------------getTimeout"+redisConfig.getTimeout()*1000);

        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
        JedisPool jedisPool = new JedisPool(poolConfig,redisConfig.getHost(),redisConfig.getPort(),
                redisConfig.getTimeout()*1000,null,
                0);
       return  jedisPool;
    }

}
