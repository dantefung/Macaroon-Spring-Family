package com.dantefung.thinkinginspringbootsamples.annotation;

import com.dantefung.thinkinginspringbootsamples.sample.server.Server;
import com.dantefung.thinkinginspringbootsamples.sample.server.impl.ServerImportBeanDefinitionRegistrar;
//import com.dantefung.thinkinginspringbootsamples.sample.server.impl.ServerImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Title: EnableServer
 * @Description: 基于“接口编程”实现@Enable模块
 * @author DANTE FUNG
 * @date 2020/3/27 13:38
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import(ServerImportSelector.class) // 导入ServerImportSelector
@Import(ServerImportBeanDefinitionRegistrar.class)// 替换ServerImportSelector
public @interface EnableServer {

	/**
	 * 设置服务器累心
	 * @return non-null
	 */
	Server.Type type();
}
