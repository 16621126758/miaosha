package com.hand.miaosha.rabbitmq;

import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.vo.LoginVo;
import org.apache.ibatis.annotations.SelectKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @Class: MQSender
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-21 14:30
 */
@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;
    private static Logger LOG = LoggerFactory.getLogger(MQReceiver.class);
//    public void send(Object message){
//        String msg = RedisService.beanToString(message);
//        LOG.info("send message :"+msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg );
//    }
//
//    public void sendtopic(Object message){
//        String msg = RedisService.beanToString(message);
//        LOG.info("sendtopic message:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",msg+"1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2",msg+"2");
//    }
//
//    public void sendFanout(Object message){
//        String msg = RedisService.beanToString(message);
//        LOG.info("send Fanout message"+msg);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"",msg);
//    }
//
//    public void sendHeaders(Object message){
//        String msg = RedisService.beanToString(message);
//        LOG.info("send headers message"+msg);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("header1","value1");
//        properties.setHeader("header2","value2");
//        Message obj = new Message(msg.getBytes(),properties);
//        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE,"",obj);
//
//    }

    //使用Direct模式交换机
    public void sendMiaoshaMessage(MiaoshaMessage mm) {
        String msg = RedisService.beanToString(mm);
        LOG.info("send message :"+msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg );
    }
}
