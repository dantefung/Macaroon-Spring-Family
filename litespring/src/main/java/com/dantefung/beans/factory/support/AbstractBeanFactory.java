package com.dantefung.beans.factory.support;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.BeansException;
import com.dantefung.beans.factory.*;
import com.dantefung.beans.factory.config.BeanPostProcessor;
import com.dantefung.util.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: DANTE FUNG
 * @Date: 2021/3/3 14:03
 * @since JDK 1.8
 * @return:
 */
@Slf4j
public abstract class AbstractBeanFactory implements BeanFactory {

	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

	private final List<String> beanDefinitionNames = new ArrayList<String>();

	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

	private ClassLoader beanClassLoader;

	@Override
	public Object getBean(String name) throws Exception {
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		if (beanDefinition == null) {
			throw new IllegalArgumentException("No bean named " + name + " is defined");
		}
		Object bean = beanDefinition.getBean();
		if (bean == null) {
			bean = doCreateBean(beanDefinition);
			bean = initializeBean(bean, name, beanDefinition);
			beanDefinition.setBean(bean);
		}
		return bean;
	}

	protected Object initializeBean(Object bean, String beanName, BeanDefinition mbd) throws Exception {

		// TODO: invokeAwareMethods
		// 实现BeanNameAware、BeanClassLoaderAware、BeanFacotryAware的回调
		//log.info("--->尝试调用{}的xxxxAware回调...", bean);
		invokeAwareMethods(beanName, bean);

		// 实现BeanPostProcessor接口的InitializingBean的前置处理
		//log.info("--->尝试调用{}的BeanPostProcessorsBeforeInitialization回调...", bean);
		applyBeanPostProcessorsBeforeInitialization(bean, beanName);

		// TODO:call initialize method
		try {
			//log.info("--->尝试调用{}的InitializeBean回调和init-method指定的方法...", bean);
			invokeInitMethods(beanName, bean, mbd);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		// 实现BeanPostProcessor接口的InitializingBean的后置处理
		//log.info("--->尝试调用{}的BeanPostProcessorsAfterInitialization回调...", bean);
		applyBeanPostProcessorsAfterInitialization(bean, beanName);
		return bean;
	}

	protected void invokeInitMethods(String beanName, Object bean, BeanDefinition mbd) throws Throwable {
		boolean isInitializingBean = (bean instanceof InitializingBean);
		// 调用afterPropertiesSet方法.
		// 省略基于安全性考虑的AccessController来调用afterPropertiesSet
		if (isInitializingBean/**!mbd.isExternallyManagedInitMethod("afterPropertiesSet")*/) {
			log.info("调用{}的afterPropertiesSet方法...", bean);
			((InitializingBean) bean).afterPropertiesSet();
		}

		// 调用initMethod
		if (mbd != null /*&& bean.getClass() != NullBean.class*/) {
			String initMethodName = mbd.getInitMethodName();
			if (StringUtils.hasLength(initMethodName) &&
					!(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) /*&&
					!mbd.isExternallyManagedInitMethod(initMethodName)*/) {
				invokeCustomInitMethod(beanName, bean, mbd);
			}
		}
	}

	protected void invokeCustomInitMethod(String beanName, Object bean, BeanDefinition mbd) throws Throwable {
		String initMethodName = mbd.getInitMethodName();
		Assert.state(initMethodName != null, "No init method set");
		Method initMethod = (((GenericBeanDefinition)mbd).isNonPublicAccessAllowed() ?
				BeanUtils.findMethod(bean.getClass(), initMethodName) :
				ClassUtils.getMethodIfAvailable(bean.getClass(), initMethodName));

		if (initMethod == null) {
			if (log.isTraceEnabled()) {
				log.trace("No default init method named '" + initMethodName +
						"' found on bean with name '" + beanName + "'");
			}
		}

		if (log.isTraceEnabled()) {
			log.trace("Invoking init method  '" + initMethodName + "' on bean with name '" + beanName + "'");
		}

		Method methodToInvoke = ClassUtils.getInterfaceMethodIfPossible(initMethod);
		// Java安全模型 参见: https://developer.ibm.com/zh/articles/j-lo-javasecurity/
		if (System.getSecurityManager() != null) {
			/*AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				ReflectionUtils.makeAccessible(methodToInvoke);
				return null;
			});
			try {
				AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () ->
						methodToInvoke.invoke(bean), getAccessControlContext());
			}
			catch (PrivilegedActionException pae) {
				InvocationTargetException ex = (InvocationTargetException) pae.getException();
				throw ex.getTargetException();
			}*/
		}
		else {
			try {
				log.info("调用{}的init-method指定的{}方法...", bean, methodToInvoke);
				ReflectionUtils.makeAccessible(methodToInvoke);
				methodToInvoke.invoke(bean);
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}
	}

	protected void invokeAwareMethods(String beanName, Object bean) {
		if (bean instanceof Aware) {
			if (bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(beanName);
			}
			if (bean instanceof BeanClassLoaderAware) {
				ClassLoader bcl = getBeanClassLoader();
				if (bcl != null) {
					((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
				}
			}
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(AbstractBeanFactory.this);
				//				((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
			}
		}
	}


	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		return beanDefinition.getBeanClass().newInstance();
	}

	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
		beanDefinitionMap.put(name, beanDefinition);
		beanDefinitionNames.add(name);
	}

	public void preInstantiateSingletons() throws Exception {
		for (Iterator it = this.beanDefinitionNames.iterator(); it.hasNext(); ) {
			String beanName = (String) it.next();
			// TODO: FacotryBean的实现
			getBean(beanName);
		}
	}

	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
		Object bean = createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		applyPropertyValues(bean, beanDefinition);
		return bean;
	}

	protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

	}

	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
		this.beanPostProcessors.add(beanPostProcessor);
	}

	public List getBeansForType(Class type) throws Exception {
		List beans = new ArrayList<Object>();
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = beanDefinitionMap.get(beanDefinitionName);
			Class beanClass = beanDefinition.getBeanClass();
			if (null != beanDefinition && null != beanClass) {
				System.out.println("beanDefinition:" + beanDefinition + ", beanClass: " + beanClass);
				if (type.isAssignableFrom(beanClass)) {
					beans.add(getBean(beanDefinitionName));
				}
			}
		}
		return beans;
	}

	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException {

		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}

	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException {

		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}

	/**
	 * Return the list of BeanPostProcessors that will get applied
	 * to beans created with this factory.
	 */
	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}

	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

}
