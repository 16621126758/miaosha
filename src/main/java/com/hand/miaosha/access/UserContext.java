package com.hand.miaosha.access;

import com.hand.miaosha.domain.MiaoshaUser;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.w3c.dom.UserDataHandler;

import java.util.Queue;

/**
 * @Class: UserContext
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-26 10:38
 */
public class UserContext {
    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<>();
    public static void setUser(MiaoshaUser user){
        userHolder.set(user);
    }

    public static MiaoshaUser getUser(){
        return userHolder.get();
    }
}
