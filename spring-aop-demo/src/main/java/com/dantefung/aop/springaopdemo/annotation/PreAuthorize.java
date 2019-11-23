package com.dantefung.aop.springaopdemo.annotation;

import java.lang.annotation.*;

/**
 * PreAuthorize注解
 *
 * @author L.cm
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {

	/**
	 * sp-el
	 * @return {String}
	 */
	String value();
}
