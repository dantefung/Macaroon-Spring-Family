package com.dantefung.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.dantefung.beans.BeanDefinition;
import com.dantefung.beans.factory.BeanDefinitionStoreException;
import com.dantefung.beans.factory.support.BeanDefinitionRegistry;
import com.dantefung.beans.factory.support.GenericBeanDefinition;
import com.dantefung.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlBeanDefinitionReader {
	
	public static final String ID_ATTRIBUTE = "id";	

	public static final String CLASS_ATTRIBUTE = "class";
	
	public static final String SCOPE_ATTRIBUTE = "scope";
	
	BeanDefinitionRegistry registry;
	
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
		this.registry = registry;
	}

	/**
	 * 配置文件就会解析成一个个 Bean 定义，注册到 BeanFactory 中，
	 * 当然，这里说的 Bean 还没有初始化，只是配置信息都提取出来了，
	 * 注册也只是将这些信息都保存到了注册中心(说到底核心是一个 beanName-> beanDefinition 的 map)
	 * @param resource: 资源文件
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/11 15:37
	 * @since JDK 1.8		
	 * @return: void
	 */
	public void loadBeanDefinitions(Resource resource){
		InputStream is = null;
		try{			
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			
			Element root = doc.getRootElement(); //<beans>
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
				if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {					
					bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));					
				}
				this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {		
			throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(),e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}
		
	}
}
