package com.dantefung.context.support02;

import com.dantefung.beans.factory.config.BeanPostProcessor;
import com.dantefung.beans.factory.support.AbstractBeanFactory;
import com.dantefung.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description:
 * @Author: DANTE FUNG
 * @Date: 2021/3/3 15:23
 * @since JDK 1.8
 * @return:
 */
@Slf4j
public abstract class AbstractApplicationContext implements ApplicationContext {

	protected AbstractBeanFactory beanFactory;

	public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void refresh() throws Exception {

		// 加载bean定义
		log.info("加载bean定义...");
		loadBeanDefinitions(beanFactory);

		// Allows post-processing of the bean factory in context subclasses.
		// TODO: postProcessBeanFactory(beanFactory);

		// Invoke factory processors registered as beans in the context.
		// TODO: invokeBeanFactoryPostProcessors(beanFactory);

		// 注册 BeanPostProcessor 的实现类，注意看和 BeanFactoryPostProcessor 的区别
		// 此接口两个方法: postProcessBeforeInitialization 和 postProcessAfterInitialization
		// 两个方法分别在 Bean 初始化之前和初始化之后得到执行。注意，到这里 Bean 还没初始化
		log.info("执行[registerBeanPostProcessors]方法， 注册 BeanPostProcessor 的实现类...");
		registerBeanPostProcessors(beanFactory);


		// 从方法名就可以知道，典型的模板方法(钩子方法)，
		// 具体的子类可以在这里初始化一些特殊的 Bean（在初始化 singleton beans 之前）
		// Initialize other special beans in specific context subclasses.
		log.info("执行[onRefresh]方法, 初始化一些特殊的 Bean（在初始化 singleton beans 之前 ...）");
		onRefresh();


		// TODO: 注册事件监听器，监听器需要实现 ApplicationListener 接口。这也不是我们的重点，过
		// Check for listener beans and register them.

		// 重点，重点，重点
		// 初始化所有的 singleton beans
		//（lazy-init 的除外）
		log.info("执行[finishBeanFactoryInitialization]方法, 初始化所有的 singleton beans lazy-init 的除外 ...");
		finishBeanFactoryInitialization();

		// 最后，广播事件，ApplicationContext 初始化完成
		// TODO: Last step: publish corresponding event. 发布相应的事件
		finishRefresh();
	}

	protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;

	protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
		List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
		for (Object beanPostProcessor : beanPostProcessors) {
			beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
		}
	}

	protected void onRefresh() throws Exception {
		//beanFactory.preInstantiateSingletons();
	}

	@Override
	public Object getBean(String name) throws Exception {
		return beanFactory.getBean(name);
	}

	/**
	 * Finish the initialization of this context's bean factory,
	 * initializing all remaining singleton beans.
	 * 完成了context里边的bean工厂， 接着初始化剩余的单例bean
	 */
	protected void finishBeanFactoryInitialization() throws Exception {
		// ...

		// TODO: Spring LTW 相关, LoadTimeWeaverAware

		// Instantiate all remaining (non-lazy-init) singletons.
		beanFactory.preInstantiateSingletons();
	}

	/**
	 * Finish the refresh of this context, invoking the LifecycleProcessor's
	 * onRefresh() method and publishing the
	 * {@link org.springframework.context.event.ContextRefreshedEvent}.
	 */
	protected void finishRefresh() {
		// TODO: 待完成
		log.info("TODO: 待完成 ...");
	}
}
