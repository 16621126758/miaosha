package com.hand.miaosha.dao;

import com.hand.miaosha.vo.GoodsData;
import com.hand.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: GoodsDao
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-14 10:45
 */
public interface GoodsDao {
    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodVoByGoodsId(long goodsId);
}
