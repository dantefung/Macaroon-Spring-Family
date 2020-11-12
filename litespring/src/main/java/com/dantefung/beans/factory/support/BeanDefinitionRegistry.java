package com.dantefung.beans.factory.support;


import com.dantefung.beans.BeanDefinition;
/**
 * @Description: 定义了Bean的各种增删改查操作.
 * @Author: DANTE FUNG
 * @Date: 2020/11/11 15:15
 * @since JDK 1.8
 */
public interface BeanDefinitionRegistry {
	BeanDefinition getBeanDefinition(String beanID);
	void registerBeanDefinition(String beanID, BeanDefinition bd);
}