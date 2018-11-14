package com.hand.miaosha.result;

import org.apache.ibatis.ognl.OgnlRuntime;

/**
 * @Class: CodeMsg
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-06 11:24
 */
public class CodeMsg {

    private int code;
    private String msg;

    //通用的异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101,"参数校验异常：%s");

    //登陆异常5002xx
    private static CodeMsg SESSION_ERROR = new CodeMsg(500210,"session已经失效或不存在");
    public static  CodeMsg PASSWORD_EMPTY =  new CodeMsg(500211,"密码不能为空");
    public static  CodeMsg MOBILE_EMPTU =  new CodeMsg(500212,"手机号码不能为空");
    public static  CodeMsg MOBILE_ERROR =  new CodeMsg(500213,"手机号码格式错误");
    public static  CodeMsg NOT_EXIST =  new CodeMsg(500214,"手机号码不存在");
    public static  CodeMsg PASSWORD_ERROR =  new CodeMsg(5002145,"密码错误");


    //商品模块5003xx

    //订单模块5004xx

    //秒杀模块5005xx

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object...args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }
}
