package com.hand.miaosha.service;

import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.vo.GoodsVo;

/**
 * @Class: MiaoshaService
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-16 09:27
 */
public interface MiaoshaService {
    OrderInfo miaosha(MiaoshaUser user, GoodsVo goodss);
}
