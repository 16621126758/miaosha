package com.hand.miaosha.service;

import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.vo.GoodsVo;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @Class: MiaoshaService
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-16 09:27
 */
public interface MiaoshaService {
    OrderInfo miaosha(MiaoshaUser user, GoodsVo goodss);

    long getMiaoshaRestult(Long id, long goodsId);

    void reset(List<GoodsVo> goodsList);

    boolean checkPath(MiaoshaUser user, long goodsId, String path);

    String createMiaoshaPath(MiaoshaUser user,long goodsId);

    BufferedImage createMiaoshaVerifyCode(MiaoshaUser user, long goodsId);

    boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode);
}
