package com.hand.miaosha.dao;



import com.hand.miaosha.domain.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Class: UserMapper
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-06 15:14
 */


public interface UserMapper {
   // @Select("select * from demo")
    List<User> query();

    int insert(User user);


}
