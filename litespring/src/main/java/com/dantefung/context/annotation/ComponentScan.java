package com.dantefung.context.annotation;

import java.lang.annotation.*;

/**
 * @Title: ComponentScan
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/04 21/17
 * @since JDK1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {

	String value() default "";
}
