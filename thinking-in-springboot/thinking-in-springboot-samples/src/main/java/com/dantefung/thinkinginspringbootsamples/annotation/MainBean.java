package com.dantefung.thinkinginspringbootsamples.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface MainBean {

	@AliasFor("value") String beanName() default "";

	@AliasFor("beanName") String value() default "";
} 