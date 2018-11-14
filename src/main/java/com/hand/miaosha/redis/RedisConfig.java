package com.hand.miaosha.redis;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import sun.security.jgss.GSSCaller;

/**
 * @Class: RedigConfig
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 09:46
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
   // @Value("${redis.host}")
    private  String host;

    private int port;

    private int timeout;   //秒

    private String password;

    private int poolMaxIdle;
    private int poolMaxWait;//秒
    private int poolMaxTotal;

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) {
        RedisConfig redisConfig = new RedisConfig();
        System.out.println(redisConfig.getHost());
    }



}
