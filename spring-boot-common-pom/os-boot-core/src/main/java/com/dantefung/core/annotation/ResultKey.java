package com.dantefung.core.annotation;

import java.lang.annotation.*;


/**
 * 对象返回注解
 * @Description
 * @Param null
 * @Return
 * @Author heyan
 * @Date 2019/7/3
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResultKey {
	String value() default "";
}
