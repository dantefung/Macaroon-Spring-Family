package com.dantefung.context.support;


import com.dantefung.core.io.FileSystemResource;
import com.dantefung.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

	/**
	 * @Description:  构造函数需要一个 xml 配置文件在系统中的路径
	 * @param path: xml配置文件在文件系统中的路径
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/11 15:28
	 * @since JDK 1.8
	 * @return:
	 */
	public FileSystemXmlApplicationContext(String path) {
		super(path);		
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(path);
	}	

}
