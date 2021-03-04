package com.dantefung.beans.factory.config;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.PropertyValue;
import com.dantefung.beans.factory.BeanFactoryAware;
import com.dantefung.beans.factory.support.AbstractBeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description: 可自动装配内容的BeanFactory
 * @Author: DANTE FUNG
 * @Date: 2021/3/3 14:15
 * @since JDK 1.8
 * @return:
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

	protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
		if (bean instanceof BeanFactoryAware) {
			((BeanFactoryAware) bean).setBeanFactory(this);
		}
		for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
			Object value = propertyValue.getValue();
			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}

			try {
				Method declaredMethod = bean.getClass().getDeclaredMethod(
						"set" + propertyValue.getName().substring(0, 1).toUpperCase()
								+ propertyValue.getName().substring(1), value.getClass());
				declaredMethod.setAccessible(true);

				declaredMethod.invoke(bean, value);
			} catch (NoSuchMethodException e) {
				Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				declaredField.setAccessible(true);
				declaredField.set(bean, value);
			}
		}
	}
}
