package com.dantefung.aop.springaopdemo.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)//注解作用于方法
public @interface LogAppSysEvent {

    String appsysid();

    String eventcode();

    String remark() default "";
}
