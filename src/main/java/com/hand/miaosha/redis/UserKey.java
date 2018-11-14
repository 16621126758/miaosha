package com.hand.miaosha.redis;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @Class: UserKey
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 17:44
 */
public class UserKey extends BasePrefix {

//    public UserKey(int expireSecond, String prefix) {
//        super(expireSecond, prefix);
//    }

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");






    }

