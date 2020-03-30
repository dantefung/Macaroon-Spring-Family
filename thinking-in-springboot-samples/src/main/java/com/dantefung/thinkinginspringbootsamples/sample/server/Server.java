package com.dantefung.thinkinginspringbootsamples.sample.server;

/**
 * @Title: Server
 * @Description: 服务器接口
 * @author DANTE FUNG
 * @date 2020/3/27 19:01
 */
public interface Server {

	/**
	 * 启动服务器
	 */
	void start();

	/**
	 * 关闭服务器
	 */
	void stop();

	/**
	 * 服务器类型
	 */
	enum Type {

		HTTP, // HTTP服务器
		FTP  // FTP服务器
	}

}
