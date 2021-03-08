package com.dantefung.beans.factory.support;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.BeansException;
import com.dantefung.beans.factory.*;
import com.dantefung.beans.factory.config.BeanPostProcessor;
import com.dantefung.util.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
		return doGetBean(name, null, null, false);
//		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
//		if (beanDefinition == null) {
//			throw new IllegalArgumentException("No bean named " + name + " is defined");
//		}
//		Object bean = beanDefinition.getBean();
//		if (bean == null) {
//			bean = doCreateBean(beanDefinition);
//			bean = initializeBean(bean, name, beanDefinition);
//			beanDefinition.setBean(bean);
//		}
//		return bean;
	}

	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * 返回指定bean的一个实例，该实例可以是共享的，也可以是独立的。
	 * @param name the name of the bean to retrieve  要检索的bean的名称
	 * @param requiredType the required type of the bean to retrieve  要检索的bean的所需类型
	 * @param args arguments to use when creating a bean instance using explicit arguments
	 *                使用显式参数创建bean实例时使用的参数
	 * (only applied when creating a new instance as opposed to retrieving an existing one)
	 *             (仅在创建新实例而不是检索现有实例时应用)
	 * @param typeCheckOnly whether the instance is obtained for a type check,
	 *  是否为类型检查获取实例，
	 * not for actual use  非实际使用
	 * @return an instance of the bean
	 * @throws BeansException if the bean could not be created
	 */
	@SuppressWarnings("unchecked")
	protected <T> T doGetBean(
			final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly)
			throws Exception {

		log.info("执行doGetBean() ...");

//		final String beanName = transformedBeanName(name);
		final String beanNaem = name;
		Object bean = null;

		// TODO: Eagerly check singleton cache for manually registered singletons.
		// 急切地检查手动注册的单例缓存。

		// Fail if we're already creating this bean instance:
		// We're assumably within a circular reference.
//		if (isPrototypeCurrentlyInCreation(beanName)) {
//			throw new BeanCurrentlyInCreationException(beanName);
//		}

		// Check if bean definition exists in this factory.


		BeanDefinition mbd = beanDefinitionMap.get(name);
		if (mbd == null) {
			throw new IllegalArgumentException("No bean named " + name + " is defined");
		}
		// Guarantee initialization of beans that the current bean depends on.

		// Create bean instance.
		// 简化实现版本 - DANTE FUNG
		if (mbd.isSingleton()) {
			bean = createBean(name, mbd, null);
			// TODO: 三级缓存
//			bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
		} else if (mbd.isPrototype()) {
			// It's a prototype -> create a new instance.
//			Object prototypeInstance = null;
//			try {
//				beforePrototypeCreation(beanName);
//				prototypeInstance = createBean(beanName, mbd, args);
//			}
//			finally {
//				afterPrototypeCreation(beanName);
//			}
//			bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
		}


		// Check if required type matches the type of the actual bean instance.


		return (T) bean;
	}

	protected Object initializeBean(Object bean, String beanName, BeanDefinition mbd) throws Exception {
		log.info("执行initializeBean() ...");
		// TODO: invokeAwareMethods
		// 实现BeanNameAware、BeanClassLoaderAware、BeanFacotryAware的回调
		//log.info("--->尝试调用{}的xxxxAware回调...", bean);
		invokeAwareMethods(beanName, bean);

		// 实现BeanPostProcessor接口的InitializingBean的前置处理
		//log.info("--->尝试调用{}的BeanPostProcessorsBeforeInitialization回调...", bean);
		Object wrappedBean = bean;
		if (mbd == null /*|| !mbd.isSynthetic()*/) {
			wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
		}

		// TODO:call initialize method
		try {
			//log.info("--->尝试调用{}的InitializeBean回调和init-method指定的方法...", bean);
			invokeInitMethods(beanName, bean, mbd);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		// 实现BeanPostProcessor接口的InitializingBean的后置处理
		//log.info("--->尝试调用{}的BeanPostProcessorsAfterInitialization回调...", bean);
		if (mbd == null /*|| !mbd.isSynthetic()*/) {
			// AOP入口3: 普通需要生成代理对象的场景。
			// 它会调用到內置的AbstractAutoProxyCreator类postProcessAfterInitialization方法：
			// 该方法中能看到我们熟悉的面孔：wrapIfNecessary方法。从上面得知该方法里面包含了真正生成代理对象的逻辑。
			wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
		}
		return wrappedBean;
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
		log.info("执行createBeanInstance()...");
		return beanDefinition.getBeanClass().newInstance();
	}

	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
		beanDefinitionMap.put(name, beanDefinition);
		beanDefinitionNames.add(name);
	}

	public void preInstantiateSingletons() throws Exception {

		List<String> beanNames = new ArrayList<String>(this.beanDefinitionNames);

		// Trigger initialization of all non-lazy singleton beans...
		for (String beanName : beanNames ) {
			// 只会对非延迟单例bean进行初始化
			// 1. 是FactoryBan
			// TODO: FacotryBean的实现
			// 2. 普通Bean
			getBean(beanName);
		}

		// 这里是针对Spring提供的SmartInitializingSingleton接口的处理.提供一个在所有Bean都创建后才调用的初始化方法
		// 参见: 《Spring Boot 小组件 - SmartInitializingSingleton.md》
		// Trigger post-initialization callback for all applicable beans...
		for (String beanName : beanNames) {
//			Object singletonInstance = getSingleton(beanName);
			Object singletonInstance = null;
			if (singletonInstance instanceof SmartInitializingSingleton) {
				final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
				if (System.getSecurityManager() != null) {
//					AccessController.doPrivileged(new PrivilegedAction<Object>() {
//						@Override
//						public Object run() {
//							smartSingleton.afterSingletonsInstantiated();
//							return null;
//						}
//					}, getAccessControlContext());
				}
				else {
					smartSingleton.afterSingletonsInstantiated();
				}
			}
		}
	}

	protected Object createBean(String beanName, BeanDefinition mbd, Object[] args) throws Exception {
		log.info("执行createBean()...");
		BeanDefinition mbdToUse = mbd;

		// Make sure bean class is actually resolved at this point, and
		// clone the bean definition in case of a dynamically resolved Class
		// which cannot be stored in the shared merged bean definition.
		// TODO:

		// TODO: Prepare method overrides.

		try {
			// Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
			// AOP入口1: AbstracAutowireCapableBeanFacotry类中 它通过BeanPostProcessor提供了一个生成代理对象的机会。
			// 具体逻辑在AbstractAutoProxyCreator类的postProcessBeforeInstantiation方法中。需要实现TargetSource才有可能会生成代理对象。
			// 该接口是对Target目标对象的封装，通过该接口可以获取到目标对象的实例。
			//  这里生成对象有什么用呢?
			// 有时我们想自己控制bean的创建和初始化，而不是通过Spring容器，这时就可以通过实现TargetSource满足要求。
			// 只是创建单纯的实例还好，如果我們想使用代理该怎么办呢？这时候，入口1的作用就体现出来了。
			Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
			if (bean != null) {
				return bean;
			}
		}
		catch (Throwable ex) {
			throw new BeanCreationException(beanName,
					"BeanPostProcessor before instantiation of bean failed", ex);
		}


		Object beanInstance = doCreateBean(beanName, mbdToUse, args);
		if (log.isDebugEnabled()) {
			log.debug("Finished creating instance of bean '" + beanName + "'");
		}
		return beanInstance;
	}

	/**
	 * Apply before-instantiation post-processors, resolving whether there is a
	 * before-instantiation shortcut for the specified bean.
	 * 应用实例化前后处理程序，解析是否存在 指定bean的实例化前快捷方式。
	 * @param beanName the name of the bean
	 * @param mbd the bean definition for the bean
	 * @return the shortcut-determined bean instance, or {@code null} if none
	 */
	protected Object resolveBeforeInstantiation(String beanName, BeanDefinition mbd) {
		Object bean = null;
		//if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
			// Make sure bean class is actually resolved at this point.
			//if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
//				Class<?> targetType = determineTargetType(beanName, mbd);
//				if (targetType != null) {
//					bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
//					if (bean != null) {
//						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
//					}
//				}
			//}
			//mbd.beforeInstantiationResolved = (bean != null);
		//}
		return bean;
	}

	protected Object doCreateBean(final String beanName, final BeanDefinition mbd, final Object[] args)
			throws Exception {
		log.info("执行doCreateBean()...");
		// Instantiate the bean.
		// 简化版本
		Object bean = createBeanInstance(mbd);
		mbd.setBean(bean);
		applyPropertyValues(bean, mbd);

		// TODO: Allow post-processors to modify the merged bean definition.

		// AOP入口2: 解决代理对象循环依赖的场景
		// 它主要作用是为了解决对象的循环依赖问题，核心思路是提前暴露singletonFactory到缓存中。
		// TODO: Eagerly cache singletons to be able to resolve circular references
		// even when triggered by lifecycle interfaces like BeanFactoryAware.
//		boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
//				isSingletonCurrentlyInCreation(beanName));
//		if (earlySingletonExposure) {
//			if (logger.isDebugEnabled()) {
//				logger.debug("Eagerly caching bean '" + beanName +
//						"' to allow for resolving potential circular references");
//			}
//			addSingletonFactory(beanName, new ObjectFactory<Object>() {
//				@Override
//				public Object getObject() throws BeansException {
//					return getEarlyBeanReference(beanName, mbd, bean);
//				}
//			});
//		}

		// Initialize the bean instance.
		bean = initializeBean(bean, beanName, mbd);
		mbd.setBean(bean);


		// TODO: Register bean as disposable.

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
