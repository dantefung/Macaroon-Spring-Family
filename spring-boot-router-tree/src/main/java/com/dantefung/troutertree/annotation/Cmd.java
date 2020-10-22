package com.dantefung.troutertree.annotation;

import java.lang.annotation.*;

/**
 * @Title: Cmd
 * @Description: 指令码注解
 * @author DANTE FUNG
 * @date 2020/10/22 22:43
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cmd {

	String value() default "";

	String version() default "";
}
