package com.dantefung.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 自定义注解封装-api请求<br/>
 * 使用注解不会默认封装返回值
 * @author DANTE FUNG
 * @version 1.0
 * Created in 2019/7/12 14:22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface ApiMethodMapping {

	/**
	 * Alias for {@link RequestMapping#name}.
	 */
	@AliasFor(annotation = RequestMapping.class) String name() default "";

	/**
	 * Alias for {@link RequestMapping#value}.
	 */
	@AliasFor(annotation = RequestMapping.class) String[] value() default {};

	/**
	 * Alias for {@link RequestMapping#path}.
	 */
	@AliasFor(annotation = RequestMapping.class) String[] path() default {};

	/**
	 * 默认请求为POST
	 * Alias for {@link RequestMapping#path}.
	 */
	@AliasFor(annotation = RequestMapping.class) RequestMethod[] method() default {RequestMethod.POST};

	@AliasFor(annotation = RequestMapping.class) String[] params() default {};

	@AliasFor(annotation = RequestMapping.class) String[] headers() default {};

	@AliasFor(annotation = RequestMapping.class) String[] consumes() default {};

	@AliasFor(annotation = RequestMapping.class) String[] produces() default {};
}
