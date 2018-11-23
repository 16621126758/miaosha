package com.hand.miaosha.dao;

import com.hand.miaosha.domain.Goods;
import com.hand.miaosha.domain.MiaoshaGoods;
import com.hand.miaosha.vo.GoodsData;
import com.hand.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

    int reduceStock(MiaoshaGoods g);

    @Update("update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    public int resetStock(MiaoshaGoods g);

}
