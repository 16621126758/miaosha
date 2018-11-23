package com.hand.miaosha.dao;

import com.hand.miaosha.domain.MiaoshaOrder;
import com.hand.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * @Class: OrderDao
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-16 09:36
 */
public interface OrderDao {

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date) values (#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Long.class,before = false,statement = "select last_insert_id()")
    Long insert(OrderInfo orderInfo);


    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);


    OrderInfo getOrderById(long orderId);


    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from miaosha_order")
    public void deleteMiaoshaOrders();
}
