package com.hand.miaosha.exception;

import com.hand.miaosha.result.CodeMsg;

/**
 * @Class: Globle
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 16:04
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private CodeMsg cm;

    public CodeMsg getCm() {
        return cm;
    }
    public GlobalException(CodeMsg cm){
        super(cm.toString());

        this.cm = cm;
    }
}
