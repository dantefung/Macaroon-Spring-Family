package com.dantefung.beans.factory.support;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.factory.BeanCreationException;
import com.dantefung.beans.factory.config.ConfigurableBeanFactory;
import com.dantefung.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 *    BeanDefinitionRegistry 接口给DefaultBeanFactory提供了注册bean定义的能力
 * @Author: DANTE FUNG
 * @Date: 2021/3/3 14:10
 * @since JDK 1.8
 * @return:
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
		implements ConfigurableBeanFactory, BeanDefinitionRegistry {


	/**
	 * 首先是统一了编程模型，只要是围绕Spring的开发，包括框架自身的开发，最后大都转化为bean定义的注册。
	 */
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
	private ClassLoader beanClassLoader;

	public DefaultBeanFactory() {

	}

	public void registerBeanDefinition(String beanID, BeanDefinition bd) {
		this.beanDefinitionMap.put(beanID, bd);
	}

	public BeanDefinition getBeanDefinition(String beanID) {

		return this.beanDefinitionMap.get(beanID);
	}

	public Object getBean(String beanID) {
		BeanDefinition bd = this.getBeanDefinition(beanID);
		if (bd == null) {
			return null;
		}

		if (bd.isSingleton()) {
			Object bean = this.getSingleton(beanID);
			if (bean == null) {
				bean = createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		}
		return createBean(bd);
	}

	private Object createBean(BeanDefinition bd) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
		}
	}

	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}
}