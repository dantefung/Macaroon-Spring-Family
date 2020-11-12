/*
 * Copyright (C), 2015-2020
 * FileName: BeanValidationPostProcessorBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/11/12 11:09
 * Description: Spring Bean 初始化时校验 Bean 是否符合 JSR-303 规范
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/12 11:09   V1.0.0
 */
package com.dantefung.springvalidation.bootstrap;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Title: BeanValidationPostProcessorBootstrap
 * @Description: Spring Bean 初始化时校验 Bean 是否符合 JSR-303 规范
 * @author DANTE FUNG
 * @date 2020/11/12 11/09
 * @since JDK1.8
 */
@Configuration
public class BeanValidationPostProcessorBootstrap {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(BeanValidationPostProcessorBootstrap.class);
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
				.genericBeanDefinition(MyBeanValidationPostProcessor.class).getBeanDefinition();
		context.registerBeanDefinition("beanValidationPostProcessor", beanDefinition);
		context.refresh();
		context.close();
	}


	/**
	 * BeanValidationPostProcessor Bean 内部有个 boolean 类型的属性 afterInitialization，默认是 false，
	 * 如果是 false，在 postProcessBeforeInitialization 过程中对 bean 进行验证，
	 * 否则在 postProcessAfterInitialization 过程对 bean 进行验证
	 *
	 * 校验底层调用了 doValidate 方法，进一步调用 validator.validate，
	 * 默认 validator 为 HibernateValidator，validation-api 包为 JAVA 规范，
	 * Spring 默认的规范实现为 hibernate-validator 包，此 hibernate 非 ORM 框架 Hibernate
	 *
	 * @see org.springframework.validation.beanvalidation.BeanValidationPostProcessor
	 * @return
	 */
	//@Bean
	public BeanPostProcessor beanValidationPostProcessor() {
//		return new BeanValidationPostProcessor();
		return new MyBeanValidationPostProcessor();
	}

	/**
	 *
	 * @return
	 */
	@Bean
	public UserModel getUserModel() {
		UserModel userModel = new UserModel();
		userModel.setUsername(null);
		userModel.setPassword("123");
		return userModel;
	}

	@Data
	class UserModel {
		@NotNull(message = "username can not be null")
		@Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "username is illegal")
		private String username;
		@Size(min = 5, max = 10, message = "password's length is illegal")
		private String password;
	}

	class MyBeanValidationPostProcessor extends BeanValidationPostProcessor {

		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
			System.out.println("postProcessBeforeInitialization => "+bean + " => " + beanName);
			super.postProcessBeforeInitialization(bean, beanName);
			return bean;
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			System.out.println("postProcessAfterInitialization => "+bean + " => " + beanName);
			super.postProcessAfterInitialization(bean, beanName);
			return bean;
		}

	}

}
