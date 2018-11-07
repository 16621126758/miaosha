package com.hand.miaosha.result;

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

    //登陆异常5002xx

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
}
