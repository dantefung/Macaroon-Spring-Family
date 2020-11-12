/*
 * Copyright (C), 2015-2020
 * FileName: MethodValidationPostProcessorBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/11/12 13:16
 * Description: 支持方法级别的 JSR-303 规范
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/12 13:16   V1.0.0
 */
package com.dantefung.springvalidation.bootstrap;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @Title: MethodValidationPostProcessorBootstrap
 * @Description: 支持方法级别的 JSR-303 规范
 * @author DANTE FUNG
 * @date 2020/11/12 13/16
 * @since JDK1.8
 */
@Configuration
public class MethodValidationPostProcessorBootstrap {

	public static void main(String[] args) {
		// 创建BeanFactory
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		// 注册配置类
		applicationContext.register(MethodValidationPostProcessorBootstrap.class);
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.genericBeanDefinition(MethodValidationPostProcessor.class).getBeanDefinition();
		applicationContext.registerBeanDefinition("methodValidationPostProcessor", beanDefinition);
		// 刷新容器
		applicationContext.refresh();
		// 获取要校验的对象
		BeanForMethodValidation beanForMethodValidation = applicationContext.getBean(BeanForMethodValidation.class);
		// 故意违反校验规则
		beanForMethodValidation.validate(null, 100);
		// 关闭容器
		applicationContext.close();
	}

	/**
	 * @Description:
	 * @param :
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/12 14:00
	 * @since JDK 1.8
	 * @see org.springframework.validation.beanvalidation.MethodValidationPostProcessor
	 * @return: org.springframework.validation.beanvalidation.MethodValidationPostProcessor
	 */
	//@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Component
	@Validated
	public class BeanForMethodValidation {
		public void validate(@NotEmpty String name, @Min(10) int age) {
			System.out.println("validate, name: " + name + ", age: " + age);
		}
	}
}
