package com.hand.miaosha.util;

import java.util.UUID;

/**
 * @Class: UUIDUtil
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 17:01
 */
public class UUIDUtil {
    public static String uuid(){
        //原声的UUID有-需要去掉
        return UUID.randomUUID().toString().replace("-","");
    }
}
