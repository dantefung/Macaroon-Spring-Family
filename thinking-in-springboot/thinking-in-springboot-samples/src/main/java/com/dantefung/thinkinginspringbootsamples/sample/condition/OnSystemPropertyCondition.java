/*
 * Copyright (C), 2015-2018
 * FileName: OnSystemPropertyCondition
 * Author:   DANTE FUNG
 * Date:     2020/5/20 15:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/5/20 15:51   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.sample.condition;

import com.dantefung.thinkinginspringbootsamples.annotation.ConditionalOnSystemProperty;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

/**
 * @Title: OnSystemPropertyCondition
 * @Description:
 * @author DANTE FUNG
 * @date 2020/5/20 15:51
 */
public class OnSystemPropertyCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// 获取ConditionalOnSystemProperty所有属性的方法值
		MultiValueMap<String, Object> attributes = metadata
				.getAllAnnotationAttributes(ConditionalOnSystemProperty.class.getName());
		// 获取ConditionalOnSystemProperty#name()方法值(单值)
		String propertyName = (String)attributes.getFirst("name");
		// 获取ConditionalOnSystemProperty#value()方法值(单值)
		String proprertyValue = (String) attributes.getFirst("value");
		// 获取系统属性值
		String systemPropertyValue = System.getProperty(propertyName);
		// 比较系统属性值与ConditionalOnSystemProperty#value()方法值是否相等
		if (Objects.equals(systemPropertyValue, proprertyValue)) {
			System.out.printf("系统属性[名称:%s]找到匹配值:%s\n", propertyName, proprertyValue);
			return true;
		}

		return false;
	}
}
