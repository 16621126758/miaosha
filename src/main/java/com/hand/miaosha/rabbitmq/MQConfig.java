package com.hand.miaosha.rabbitmq;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;


/**
 * @Class: MQConfig
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-21 14:31
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicexchange";
    public static final String FANOUT_EXCHANGE = "fanoutexchange";
    public static final String HEADERS_EXCHANGE = "headersexchange";
    public static final String HEADERS_QUEUE = "headers.queue";
    public static final String ROUNT_KEY1= "topic.key1";
    public static final String ROUNT_KEY2= "topic.#";
    public static final String MIAOSHA_QUEUE = "miaosha.queue";



    @Bean
    public Queue queue(){
        //队列名称
        return new Queue(MQConfig.QUEUE,true);
    }

    @Bean
    public Queue queue1(){
        return new Queue(MQConfig.MIAOSHA_QUEUE,true);
    }

    /**
     * Topic模式 交换机Exchange
     * @return
     */
    @Bean
    public Queue topicQueue1(){

        return new Queue(MQConfig.TOPIC_QUEUE1,true);
    }
    @Bean
    public Queue topicQueue2(){

        return new Queue(MQConfig.TOPIC_QUEUE2,true);
    }
    @Bean  //交换机  rabbitMQ是先将消息放到交换机，然后再放入队列中
    public TopicExchange topicExchange(){
        return new TopicExchange(MQConfig.TOPIC_EXCHANGE);
    }
    @Bean
    public Binding topicbinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }
    @Bean
    public Binding topicbinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }


    /**
     * Fanout模式 交换机Exchange
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    @Bean
    public Binding FanoutBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }


    /**
     * Header模式  交换机Exchange
     */

    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }
    @Bean
    public Queue headersQueue1(){
        return new Queue(HEADERS_QUEUE,true);
    }
    @Bean
    public Binding HeaderBinding(){
        Map<String,Object> map = new HashMap<>();
        map.put("header1","value1");
        map.put("header2","value2");
        return BindingBuilder.bind(headersQueue1()).to(headersExchange()).whereAll(map).match();
    }

}
