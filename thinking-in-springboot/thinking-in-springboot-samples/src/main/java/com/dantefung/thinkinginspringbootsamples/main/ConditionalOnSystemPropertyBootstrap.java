/*
 * Copyright (C), 2015-2018
 * FileName: ConditionalOnSystemPropertyBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/5/20 16:03
 * Description: 编写OnSystemPropertyCondition的引导程序
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/5/20 16:03   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.config.ConditionalMessageConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Title: ConditionalOnSystemPropertyBootstrap
 * @Description: 编写OnSystemPropertyCondition的引导程序
 * @author DANTE FUNG
 * @date 2020/5/20 16:03
 */
public class ConditionalOnSystemPropertyBootstrap {

	public static void main(String[] args) {
		// 设置System Property language = Chinese
		System.setProperty("language", "Chinese");
		// 构建Annotation配置驱动Spring上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 注册配置Bean ConditionalMessgeConfiguration到Spring上下文
		context.register(ConditionalMessageConfiguration.class);
		// 启动上下文
		context.refresh();
		// 获取名称为"message"的Bean对象
		String message = context.getBean("message", String.class);
		// 输出message内容
		System.out.printf("\"message\"Bean对象:%s\n", message);
	}
}
