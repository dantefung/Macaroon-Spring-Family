package com.dantefung.beans.factory.xml02;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.core.io.ResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Title: XmlBeanDefinitionReaderTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/05 10/01
 * @since JDK1.8
 */
public class XmlBeanDefinitionReaderTest {

	@Test
	public void loadBeanDefinitions() throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("petstore-aware.xml");
		Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
		Assert.assertTrue(registry.size() > 0);
	}
}