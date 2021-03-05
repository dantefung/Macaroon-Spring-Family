package com.dantefung.sample.postprocessor;

import com.dantefung.beans.BeansException;
import com.dantefung.beans.factory.config.BeanPostProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanInitializeLogger implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("Initialize bean " + beanName + " start!");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("Initialize bean " + beanName + " end!");
		return bean;
	}
}