package com.hand.miaosha.rabbitmq;


import com.hand.miaosha.domain.MiaoshaOrder;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.result.CodeMsg;
import com.hand.miaosha.result.Result;
import com.hand.miaosha.service.GoodsService;
import com.hand.miaosha.service.MiaoshaService;
import com.hand.miaosha.service.OrderService;
import com.hand.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class: MQReceiver
 * @description:  这种是Direct模式 交换机Exchange
 * @Author: hongzhi.zhao
 * @Date: 2018-11-21 14:30
 */
@Service
public class MQReceiver {

    private static Logger LOG = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    /**
     * Direct  模式    交换机Exchange
     * @param message
     */
   // @RabbitListener(queues = MQConfig.QUEUE)
    //public void receiver(String message){
      //  LOG.info("receiver message :"+message);
    //}

    /**
     * Topic模式   交换机Exchange
     */
    //@RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    //public void receiverTopic1(String message){
      //  LOG.info(" topic queue1 message : "+message);
    //}

   // @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    //public void receiverTopic2(String message){
       // LOG.info(" topic queue2 message : "+message);
    //}

    /**
     * Header模式  交换机Exchange
     */

    //@RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    //public void receiverRabbit(byte[] message){
      //  LOG.info("headers queue message"+new String(message));

    //}


    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiver(String message){
        LOG.info("receiver  message :"+message);
        MiaoshaMessage mm = RedisService.StringToBean(message,MiaoshaMessage.class);
        MiaoshaUser user = mm.getMiaoshaUser();
        long goodsId = mm.getGoodsId();
        //判断库存
           GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock<=0){
            //model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER);
            return ;
        }
        //判断是否已经秒杀过了
        MiaoshaOrder miaoshaOrder =  orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (null!=miaoshaOrder){
            return;
        }
        //减少库存，下订单，写入秒杀订单
        miaoshaService.miaosha(user,goods);

    }
}
