package com.hand.miaosha.service.Impl;

import com.hand.miaosha.dao.UserMapper;
import com.hand.miaosha.domain.User;
import com.hand.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class: UserServiceImpl
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-07 16:50
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;



    @Override
    public int demo() {
        return 123;
    }


    @Transactional
    public boolean tx(){
        User user1 = new User();
        user1.setId(4);
        user1.setName("222222");
        userMapper.insert(user1);

        User user = new User();
        user.setId(1);
        user.setName("11111");
        userMapper.insert(user);

        return true;
    }
}
