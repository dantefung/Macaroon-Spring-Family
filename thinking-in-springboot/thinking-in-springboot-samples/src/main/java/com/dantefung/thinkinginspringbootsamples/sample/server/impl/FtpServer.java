/*
 * Copyright (C), 2015-2018
 * FileName: FtpServer
 * Author:   DANTE FUNG
 * Date:     2020/3/27 19:16
 * Description: FTP服务器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/27 19:16   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.sample.server.impl;

import com.dantefung.thinkinginspringbootsamples.sample.server.Server;
import org.springframework.stereotype.Component;

/**
 * @Title: FtpServer
 * @Description: FTP服务器
 * @author DANTE FUNG
 * @date 2020/3/27 19:16
 */
@Component
public class FtpServer implements Server {

	@Override
	public void start() {
		System.out.println("FTP服务器启动中...");
	}

	@Override
	public void stop() {
		System.out.println("FTP服务器关闭中...");
	}
}
