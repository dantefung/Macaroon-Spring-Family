package com.dantefung.test.v1;

import com.dantefung.sample.aware.GoodService;
import com.dantefung.sample.aware.OrderService;
import com.dantefung.sample.aware.UserService;
import com.dantefung.context.ApplicationContext;
import com.dantefung.context.support.ClassPathXmlApplicationContext;
import com.dantefung.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;
import com.dantefung.sample.postprocessor.BeanInitializeLogger;
import com.dantefung.sample.initializing.ApplyService;

public class ApplicationContextTest {

	@Test
	public void testGetBean() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		System.out.println(petStore);
		Assert.assertNotNull(petStore);
	}
    @Test 
	public void testGetBeanFromFileSystemContext(){
	    //注意啊，这里仍然是hardcode了一个本地路径，这是不好的实践!! 如何处理，留作作业
		/*ApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\Users\\liuxin\\git-litespring\\src\\test\\resources\\petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		Assert.assertNotNull(petStore);*/
		
	}

	@Test
	public void test() throws Exception {
		ApplicationContext applicationContext = new com.dantefung.context.support02.ClassPathXmlApplicationContext("petstore-v1.xml");
		PetStoreService petStore = (PetStoreService) applicationContext.getBean("petStore");
		System.out.println(petStore);
		Assert.assertNotNull(petStore);
	}


	@Test
	public void testPostProcessor() throws Exception {
		ApplicationContext applicationContext = new com.dantefung.context.support02.ClassPathXmlApplicationContext("petstore-postprocessor.xml");
		BeanInitializeLogger beanInitializeLogger = (BeanInitializeLogger) applicationContext.getBean("beanInitializeLogger");
		System.out.println(beanInitializeLogger);
		Assert.assertNotNull(beanInitializeLogger);
	}

	@Test
	public void testAware() throws Exception {
		ApplicationContext applicationContext = new com.dantefung.context.support02.ClassPathXmlApplicationContext("petstore-aware.xml");
		UserService userService = (UserService) applicationContext.getBean("userService");
		System.out.println(userService);
		Assert.assertNotNull(userService);
		GoodService goodService = (GoodService) applicationContext.getBean("goodService");
		System.out.println(goodService);
		Assert.assertNotNull(goodService);
		OrderService orderService = (OrderService) applicationContext.getBean("orderService");
		System.out.println(orderService);
		Assert.assertNotNull(orderService);
	}

	@Test
	public void testInitializingBeanAndInitMethod() throws Exception {
		ApplicationContext applicationContext = new com.dantefung.context.support02.ClassPathXmlApplicationContext("petstore-initializing.xml");
		ApplyService applyService = (ApplyService) applicationContext.getBean("applyService");
		System.out.println(applyService);
		Assert.assertNotNull(applyService);
	}
}
