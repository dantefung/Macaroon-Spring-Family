/*
 * Copyright (C), 2015-2020
 * FileName: AbstractBeanDefinition
 * Author:   DANTE FUNG
 * Date:     2021/3/3 14:52
 * Description: 抽象BeanDefiniation
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/3 14:52   V1.0.0
 */
package com.dantefung.beans.factory.support;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.PropertyValues;

/**
 * @Title: AbstractBeanDefinition
 * @Description: 抽象Bean定义
 * @author DANTE FUNG
 * @date 2021/03/03 14/52
 * @since JDK1.8
 */
public abstract class AbstractBeanDefinition implements BeanDefinition {

	protected volatile Class beanClass;

	private PropertyValues propertyValues = new PropertyValues();


	@Override
	public Class getBeanClass() {
		return beanClass;
	}

	@Override
	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	@Override
	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}
}
