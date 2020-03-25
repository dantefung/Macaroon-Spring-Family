package com.dantefung.thinkinginspringbootsamples.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyService {

	/**
	 * 2.继承注解的功能
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";
 
}
