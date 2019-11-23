package com.dantefung.aop.springaopdemo.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogType {
	String value() default "";
}
