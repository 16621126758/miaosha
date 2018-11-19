package com.hand.miaosha.service;

import com.hand.miaosha.vo.GoodsData;
import com.hand.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * @Class: GoodsService
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-14 10:44
 */
public interface GoodsService {

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    void reduceStock(GoodsVo goods);
}
