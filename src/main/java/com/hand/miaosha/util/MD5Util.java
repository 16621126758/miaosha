package com.hand.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Class: MD5Util
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-08 21:03
 */
public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    //用户输入的密码转化成form表单提交时候的密码
    public static String inputPassFormPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    //表单的密码转化成db的密码
    public static String formPassToDbPass(String formPass,String salt){
        String str = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    //用户输入的铭文密码转成数据库密码
    public static String inputPassToDbPass(String input,String saltDB){
        //铭文密码转成form密码
        String  formPass = inputPassFormPass(input);
        //form密码转化成db密码
        String dbPass = formPassToDbPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
//        System.out.println(inputPassFormPass("123456"));
////        System.out.println(formPassToDbPass(inputPassFormPass("123456"),"1a2b3c4d"));
        System.out.println(inputPassToDbPass("123456","1a2b3c4d"));
    }
}
