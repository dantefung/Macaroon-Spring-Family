package com.dantefung.test.v1;

import static org.junit.Assert.*;


import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.factory.BeanCreationException;
import com.dantefung.beans.factory.BeanDefinitionStoreException;
import com.dantefung.beans.factory.support.DefaultBeanFactory;
import com.dantefung.beans.factory.xml.XmlBeanDefinitionReader;
import com.dantefung.core.io.ClassPathResource;
import com.dantefung.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanFactoryTest {
	DefaultBeanFactory factory = null;
	XmlBeanDefinitionReader reader = null;
	
	@Before
	public void setUp(){
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);
		
	}
	@Test
	public void testGetBean() {
		
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		assertTrue(bd.isSingleton());
		
		assertFalse(bd.isPrototype());
		
		assertEquals(BeanDefinition.SCOPE_DEFAULT,bd.getScope());
		
		assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());
		
		PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petStore);
		
		PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");
		
		assertTrue(petStore.equals(petStore1));
	}
	
	@Test
	public void testInvalidBean(){

		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		try{
			factory.getBean("invalidBean");
		}catch(BeanCreationException e){
			return;
		}
		Assert.fail("expect BeanCreationException ");
	}
	@Test
	public void testInvalidXML(){
		
		try{
			reader.loadBeanDefinitions(new ClassPathResource("xxxx.xml"));
		}catch(BeanDefinitionStoreException e){
			return;
		}
		Assert.fail("expect BeanDefinitionStoreException ");
	}

}