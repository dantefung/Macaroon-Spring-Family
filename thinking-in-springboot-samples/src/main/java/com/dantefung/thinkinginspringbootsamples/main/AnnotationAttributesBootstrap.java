/*
 * Copyright (C), 2015-2018
 * FileName: AnnotationAttributesBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/3/24 19:16
 * Description: 理解Spring注解属性抽象AnnotationAttributes
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/24 19:16   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.annotation.TransactionalService;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.AnnotatedElement;
import java.util.LinkedHashMap;

/**
 * @Title: AnnotationAttributesBootstrap
 * @Description: 理解Spring注解属性抽象AnnotationAttributes
 *
 * @Component
 *   |- @Service
 *      |- @TransactionalService
 *
 *  AnnotationAttributes采用注解就近覆盖的设计原则
 *
 * @Service较@Component而言，距@TransactionalService更近，所以它是较底层注解。
 *
 *
 * @author DANTE FUNG
 * @date 2020/3/24 19:16
 */
public class AnnotationAttributesBootstrap {

	public static void main(String[] args) {
		AnnotatedElement annotatedElement = TransactionalService.class;
		// 获取@Service注解属性独享
		AnnotationAttributes serviceAttributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(annotatedElement, Service.class);
		// 获取@Transactional注解属性独享
		AnnotationAttributes transactionalAttributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(annotatedElement, Transactional.class);
		// 输出
		print(serviceAttributes);
		print(transactionalAttributes);

	}

	private static void print(AnnotationAttributes annotationAttributes) {

		System.out.printf("注解@%s 属性集合:\n", annotationAttributes.annotationType().getName());
		// 已知public class AnnotationAttributes extends LinkedHashMap<String, Object>
		annotationAttributes.forEach((name, value) -> {
			System.out.printf("\t 属性 %s: %s \n", name, value);
		});
	}

	/**
	 * 按照层次性的分析：
 	 * @Service 的更底层注解为 @Component
	 * @Component.value() 与 @Service.value() 将发生合并，所以最终AnnotationAttributes对象仅包含一个元素，即value="testMerged"
	 */
}
