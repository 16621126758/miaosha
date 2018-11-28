package com.hand.miaosha.access;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Class: AccessLimit
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-26 10:15
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin() default true;
}
