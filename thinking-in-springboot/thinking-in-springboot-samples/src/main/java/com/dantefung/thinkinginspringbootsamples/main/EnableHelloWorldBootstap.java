/*
 * Copyright (C), 2015-2018
 * FileName: EnableHelloWorldBootstap
 * Author:   DANTE FUNG
 * Date:     2020/3/26 20:15
 * Description: 标注@EnableHelloWorld到引导类EnableHelloWorldBootstrap
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/26 20:15   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.annotation.EnableHelloWorld;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: EnableHelloWorldBootstap
 * @Description: 标注@EnableHelloWorld到引导类EnableHelloWorldBootstrap
 * @author DANTE FUNG
 * @date 2020/3/26 20:15
 */
@EnableHelloWorld
@Configuration
public class EnableHelloWorldBootstap {

	public static void main(String[] args) {
		// 构建Annotation配置驱动Spring上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 注册当前引导类(被@Configuration标注)到Spring上下文
		context.register(EnableHelloWorldBootstap.class);
		// 启动上下文
		context.refresh();
		// 获取名称为"helloworld"的Bean对象
		String helloWorld = context.getBean("helloWorld", String.class);
		// 输出用户名称: "Hello,World"
		System.out.printf("helloWord = %s \n", helloWorld);
		// 关闭上下文
		context.close();
	}
}
