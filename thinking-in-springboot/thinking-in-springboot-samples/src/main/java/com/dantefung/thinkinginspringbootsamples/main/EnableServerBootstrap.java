/*
 * Copyright (C), 2015-2018
 * FileName: EnableServerBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/3/27 19:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/27 19:25   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.annotation.EnableServer;
import com.dantefung.thinkinginspringbootsamples.sample.server.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: EnableServerBootstrap
 * @Description:
 * @author DANTE FUNG
 * @date 2020/3/27 19:25
 */
@Configuration
@EnableServer(type = Server.Type.HTTP)
public class EnableServerBootstrap {

	public static void main(String[] args) {
		// 构建Annotation配置驱动Spring上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 注册当前引导类(被@Configuration标注)到Spring上下文
		context.register(EnableServerBootstrap.class);
		// 启动上下文
		context.refresh();
		// 获取Server Bean对象，实际为HttpServer
		Server server = context.getBean(Server.class);
		// 启动服务器
		server.start();
		// 关闭服务器
		server.stop();
	}
}
