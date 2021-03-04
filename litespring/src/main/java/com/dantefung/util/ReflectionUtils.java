/*
 * Copyright (C), 2015-2020
 * FileName: ReflectionUtils
 * Author:   DANTE FUNG
 * Date:     2021/3/4 11:20 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/4 11:20 下午   V1.0.0
 */
package com.dantefung.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Title: ReflectionUtils
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/04 23/20
 * @since JDK1.8
 */
public abstract class ReflectionUtils {

	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The {@code setAccessible(true)} method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param method the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
	@SuppressWarnings("deprecation")  // on JDK 9
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) ||
				!Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
			method.setAccessible(true);
		}
	}
}
