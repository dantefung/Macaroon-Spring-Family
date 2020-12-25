/*
 * Copyright (C), 2015-2020
 * FileName: SpringContextHolder
 * Author:   DANTE FUNG
 * Date:     2020/12/24 16:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/24 16:25   V1.0.0
 */
package com.dantefung.freemarker.util;

import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

/**
 * @Title: SpringContextHolder
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/24 16/25
 * @since JDK1.8
 */
@UtilityClass
public class SpringContextHolder {

	private static ApplicationContext appContext;

	/**
	 * 通过name获取 Bean.
	 *
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return appContext.getBean(name);

	}

	/**
	 * 通过class获取Bean.
	 *
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return appContext.getBean(clazz);
	}

	/**
	 * 通过name,以及Clazz返回指定的Bean
	 *
	 * @param name
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return appContext.getBean(name, clazz);
	}

	public static void setAppContext(ApplicationContext appContext) {
		SpringContextHolder.appContext = appContext;
	}
}
