/*
 * Copyright (C), 2015-2018
 * FileName: HelloWorldConfiguration
 * Author:   DANTE FUNG
 * Date:     2020/3/26 20:10
 * Description: 实现“注解驱动实现”的"@Enable模块驱动".
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/26 20:10   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 *
 * 注解驱动：@Configuration  @Bean
 *
 * @Title: HelloWorldConfiguration
 * @Description: 实现“注解驱动实现”的"@Enable模块驱动".
 * @author DANTE FUNG
 * @date 2020/3/26 20:10
 */
@Configuration
public class HelloWorldConfiguration {

	@Bean
	public String helloWorld() {// 创建名为"helloworld" String类型的Bean
		return "Hello, World!";
	}
}
