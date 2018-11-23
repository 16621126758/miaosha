package com.hand.miaosha.service.Impl;

import com.hand.miaosha.dao.OrderDao;
import com.hand.miaosha.domain.MiaoshaOrder;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.redis.OrderKey;
import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.service.OrderService;
import com.hand.miaosha.vo.GoodsVo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Class: OrderServiceImpl
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-16 09:19
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {

        //return orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class);
    }

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        //写订单info
        OrderInfo  orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        System.out.println("OrderServiceImpl zhong de create Order fangfa goods.getMiaoshpRICE"+goods.getMiaoshaPrice());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        System.out.println("添加订单信息-=-=-=-=-=-------------------"+orderInfo.toString());
        orderDao.insert(orderInfo);
        System.out.println("orderID收到的值为++++++++++++++++"+orderInfo.getId());
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId ());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user.getId()+"_"+goods.getId(),miaoshaOrder);
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }
}
