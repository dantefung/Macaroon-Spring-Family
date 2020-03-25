package com.dantefung.thinkinginspringbootsamples.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 第一类:  注解内部的显性别名
 * 3.在同个注解中为同一个功能定义两个名称不一样的属性，那么这两个属性彼此互为别名
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface MyRequestMapping {
 
	
	String name() default "";

	/**
	 * 这么做的目的在于:
	 *
	 * 1.更便捷
	 * 当我们只定义一个属性的时候往往可以省略属性名如:
	 * @RequestMapping(“/user”)
	 *
	 * 2.顾名思义
	 * 当我门定义多个属性时为了能做到顾名思义
	 * 使之达到一目了然的效果我们需要选择一个更加贴合特定场景的名称。
	 * @RequestMapping(path = “/user”,method = RequestMethod.GET)
	 * 当然你也可以这样:
	 * @RequestMapping(value = “/user”,method = RequestMethod.GET)
	 * 只是这样子的定义value = “/user” 不能很准确地传达代码的意图。
	 */

	@AliasFor("path")
	String[] value() default {};
 
	
	@AliasFor("value")
	String[] path() default {};
 
	
	RequestMethod[] method() default {};
 
	
	String[] params() default {};
 
	
	String[] headers() default {};
 
	
	String[] consumes() default {};
 
	
	String[] produces() default {};
 
}
