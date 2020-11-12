package com.dantefung.beans.factory.config;


import com.dantefung.beans.factory.BeanFactory;

/**
 * @Description: 提供配置Facotry的各种方法.
 * @Author: DANTE FUNG
 * @Date: 2020/11/11 15:16
 * @since JDK 1.8
 */
public interface ConfigurableBeanFactory extends BeanFactory {
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();	
}