package com.hand.miaosha.dao;

import com.hand.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Class: MiaoshaUserDao
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 09:30
 */
@Mapper
public interface MiaoshaUserDao {

     MiaoshaUser getById(@Param("id") long id);

}
