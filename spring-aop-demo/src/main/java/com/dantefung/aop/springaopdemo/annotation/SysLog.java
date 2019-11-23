package com.dantefung.aop.springaopdemo.annotation;

import java.lang.annotation.*;

/**
 * @author wanweikang
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
	
	/**
	 * 操作类型
	 */
	String type();
	/**
	 * 操作内容
	 */
	String content();
	
	/**
	 * 项目编号
	 */
	String projectId() default "-1";
	
}
