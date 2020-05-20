/*
 * Copyright (C), 2015-2018
 * FileName: ServerImportSelector
 * Author:   DANTE FUNG
 * Date:     2020/3/27 19:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/27 19:19   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.sample.server.impl;

import com.dantefung.thinkinginspringbootsamples.annotation.EnableServer;
import com.dantefung.thinkinginspringbootsamples.sample.server.Server;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @Title: ServerImportSelector
 * @Description:
 * @author DANTE FUNG
 * @date 2020/3/27 19:19
 */
public class ServerImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// 读取EnableServer中所有的属性方法，本例中仅有type()属性方法
		// 其中key为属性方法的名称，value为属性方法的返回对象
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(EnableServer.class.getName());
		// 获取名为"type"的属性方法，并且强制转化成Server.Type类型
		Server.Type type = (Server.Type) annotationAttributes.get("type");
		// 导入的类名称数组
		String[] importClassNames = new String[0];
		switch (type) {
			case HTTP:// 当设置HTTP服务器类型时，返回HttpServer组件
				importClassNames = new String[]{HttpServer.class.getName()};
				break;
			case FTP:// 当设置FTP服务器类型时，返回FtpServer组件
				importClassNames = new String[]{FtpServer.class.getName()};
				break;
		}
		return importClassNames;
	}
}
