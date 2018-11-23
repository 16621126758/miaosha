package com.hand.miaosha.redis;

/**
 * @Class: MiaoshaKey
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-22 10:12
 */
public class MiaoshaKey extends BasePrefix {

    public MiaoshaKey(int expireSeconds,String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300,"vc");


}
