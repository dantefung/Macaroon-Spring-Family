/*
 * Copyright (C), 2015-2020
 * FileName: LocalizationValidateBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/11/12 16:52
 * Description: 国际化校验提示语
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/12 16:52   V1.0.0
 */
package com.dantefung.springvalidation.bootstrap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: LocalizationValidateBootstrap
 * @Description: 国际化校验提示语
 * @author DANTE FUNG
 * @date 2020/11/12 16/52
 * @since JDK1.8
 */
@Configuration
public class LocalizationValidateBootstrap {

	public static void main(String[] args) {
		// 创建容器
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		// 注册配置类
		applicationContext.register(LocalizationValidateBootstrap.class);

		// 刷新容器
		applicationContext.refresh();
		// 关闭容器
		applicationContext.close();
	}
}
