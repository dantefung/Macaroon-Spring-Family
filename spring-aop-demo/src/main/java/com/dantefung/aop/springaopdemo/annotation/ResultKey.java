package com.dantefung.aop.springaopdemo.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResultKey {
    String value() default "";
}
