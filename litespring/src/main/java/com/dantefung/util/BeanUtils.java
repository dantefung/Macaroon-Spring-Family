/*
 * Copyright (C), 2015-2020
 * FileName: BeanUtils
 * Author:   DANTE FUNG
 * Date:     2021/3/4 10:47 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/4 10:47 下午   V1.0.0
 */
package com.dantefung.util;

import java.lang.reflect.Method;

/**
 * @Title: BeanUtils
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/04 22/47
 * @since JDK1.8
 */
public class BeanUtils {

	public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			return findDeclaredMethod(clazz, methodName, paramTypes);
		}
	}

	public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			if (clazz.getSuperclass() != null) {
				return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
			}
			return null;
		}
	}
}
