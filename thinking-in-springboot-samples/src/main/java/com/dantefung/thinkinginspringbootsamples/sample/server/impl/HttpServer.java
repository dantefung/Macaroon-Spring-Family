/*
 * Copyright (C), 2015-2018
 * FileName: HttpServer
 * Author:   DANTE FUNG
 * Date:     2020/3/27 19:11
 * Description: HTTP服务器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/27 19:11   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.sample.server.impl;

import com.dantefung.thinkinginspringbootsamples.sample.server.Server;
import org.springframework.stereotype.Component;

/**
 * @Title: HttpServer
 * @Description: HTTP服务器
 * @author DANTE FUNG
 * @date 2020/3/27 19:11
 */
@Component // 根据ImportSelector的契约，请确保实现为Spring组件
public class HttpServer implements Server {

	@Override
	public void start() {
		System.out.println("HTTP服务器启动中...");
	}

	@Override
	public void stop() {
		System.out.println("HTTP服务器关闭中...");
	}
}
