package com.dantefung.test.v1;

import static org.junit.Assert.*;


import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.factory.BeanCreationException;
import com.dantefung.beans.factory.BeanDefinitionStoreException;
import com.dantefung.beans.factory.BeanFactory;
import com.dantefung.beans.factory.config.AutowireCapableBeanFactory;
import com.dantefung.beans.factory.support.AbstractBeanFactory;
import com.dantefung.beans.factory.support.DefaultBeanFactory;
import com.dantefung.beans.factory.xml.XmlBeanDefinitionReader;
import com.dantefung.core.io.ClassPathResource;
import com.dantefung.core.io.ResourceLoader;
import com.dantefung.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanFactoryTest {

	DefaultBeanFactory factory = null;
	XmlBeanDefinitionReader reader = null;

	@Before
	public void setUp() {
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);

	}

	@Test
	public void testGetBean() {

		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));

		BeanDefinition bd = factory.getBeanDefinition("petStore");

		assertTrue(bd.isSingleton());

		assertFalse(bd.isPrototype());

		assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

		PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

		assertNotNull(petStore);

		PetStoreService petStore1 = (PetStoreService) factory.getBean("petStore");

		assertTrue(petStore.equals(petStore1));
	}

	@Test
	public void testInvalidBean() {

		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		try {
			factory.getBean("invalidBean");
		} catch (BeanCreationException e) {
			return;
		}
		Assert.fail("expect BeanCreationException ");
	}

	@Test
	public void testInvalidXML() {

		try {
			reader.loadBeanDefinitions(new ClassPathResource("xxxx.xml"));
		} catch (BeanDefinitionStoreException e) {
			return;
		}
		Assert.fail("expect BeanDefinitionStoreException ");
	}

	//////////////////////////////////// BeanFactory 设计演进 ///////////////////////////////////////////

	/**
	 * BeanDefinition：用于保存bean对象以及其他额外的信息。
	 * BeanFactory：维护一个线程安全的ConcurrentHashMap集合，用于存取bean。
	 * @throws Exception
	 */
	@Test
	public void testBasicIoc() throws Exception {
		// 1、创建bean工厂


		// 2、注册bean定义


		// 3、获取bean


	}


	/**
	 *
	 * 用模板设计模式优化BeanFactory，这里把bean创建交给工厂，为了保证扩展性，
	 * 我们使用Extract Interface的方法，将BeanFactory替换成接口，
	 * 而使用AbstractBeanFactory和AutowireCapableBeanFactory作为其实现，这里用到了模板设计模式。
	 *
	 * AbstractBeanFactory：定义好获取bean和注册bean的方法，将具体实现doCreateBean的方法交给子类。
	 * AutowireCapableBeanFactory：继承模板bean工厂，实现doCreateBean方法。
	 *
	 *
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {

		// 1、初始化bean工厂
		AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();

		// 2、读取并解析xml文件
		com.dantefung.beans.factory.xml02.XmlBeanDefinitionReader beanDefinitionReader = new com.dantefung.beans.factory.xml02.XmlBeanDefinitionReader(
				new ResourceLoader());
		beanDefinitionReader.loadBeanDefinitions("petstore-ioc.xml");

		// 3、注入bean
		beanDefinitionReader.getRegistry().forEach((name, beanDefinition) -> {
			try {
				System.out.println(name + "==" + beanDefinition);
				beanFactory.registerBeanDefinition(name, beanDefinition);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// 4、初始化bean（不使用lazy-init的方式）
		beanFactory.preInstantiateSingletons();

		// 5、获取bean
		PetStoreService petStoreService = (PetStoreService) beanFactory.getBean("petStore");
		System.out.println(petStoreService);
		Assert.assertNotNull(petStoreService);

	}

}