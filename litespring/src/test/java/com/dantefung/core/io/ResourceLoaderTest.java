package com.dantefung.core.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @Title: ResourceLoaderTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/05 09/57
 * @since JDK1.8
 */
public class ResourceLoaderTest {

	@Test
	public void getResource() throws IOException {
		ResourceLoader resourceLoader = new ResourceLoader();
		Resource resource = resourceLoader.getResource("petstore-aware.xml");
		InputStream inputStream = resource.getInputStream();
		Assert.assertNotNull(inputStream);
	}
}