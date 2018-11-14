package com.hand.miaosha.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @Class: IsMobile
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 14:30
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {

    //默认必须有

    boolean required() default  true;

    String message() default "{手机号码格式有误}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
