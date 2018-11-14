package com.hand.miaosha.util;

import org.springframework.util.StringUtils;
import sun.applet.Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Class: ValidatorUtil
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-09 10:48
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern=Pattern.compile("1\\d{10}");


    public  static boolean isMobile(String str){
        if (StringUtils.isEmpty(str)){
            return false;
        }
        Matcher m = mobile_pattern .matcher(str);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("18912341234"));
        System.out.println(isMobile("1891234123"));
    }
}
