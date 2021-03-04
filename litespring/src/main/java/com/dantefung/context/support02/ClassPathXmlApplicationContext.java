package com.dantefung.context.support02;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.factory.config.AutowireCapableBeanFactory;
import com.dantefung.beans.factory.support.AbstractBeanFactory;
import com.dantefung.beans.factory.xml02.XmlBeanDefinitionReader;
import com.dantefung.core.io.ResourceLoader;
import com.dantefung.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description:
 * @Author: DANTE FUNG
 * @Date: 2021/3/3 15:31
 * @since JDK 1.8
 * @return:
 */
@Slf4j
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	private String configLocation;
	private ClassLoader beanClassLoader;

	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation, new AutowireCapableBeanFactory());
	}

	public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation = configLocation;
		refresh();
	}

	@Override
	protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
		log.info("加载bean定义...");
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
		// 这一步应该放到XmlBeanDefinitionReader中做.
		for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
			beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
		}
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}
}