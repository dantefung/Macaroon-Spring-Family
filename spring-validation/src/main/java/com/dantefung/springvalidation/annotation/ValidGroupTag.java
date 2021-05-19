package com.dantefung.springvalidation.annotation;

import java.lang.annotation.*;


/**
 * 校验组标记
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidGroupTag {
    //参数名，指定校验哪些参数，默认校验全部参数
    String[] value() default {};
}
