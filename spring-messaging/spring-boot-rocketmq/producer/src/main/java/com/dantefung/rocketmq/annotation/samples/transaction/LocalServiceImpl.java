/*
 * Copyright (C), 2015-2018
 * FileName: LocalServiceImpl
 * Author:   DANTE FUNG
 * Date:     2020/4/14 19:20
 * Description: 测试LocalService实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/14 19:20   V1.0.0
 */
package com.dantefung.rocketmq.annotation.samples.transaction;

import org.springframework.stereotype.Service;

/**
 * @Title: LocalServiceImpl
 * @Description: 测试LocalService实现
 * @author DANTE FUNG
 * @date 2020/4/14 19:20
 */
@Service
public class LocalServiceImpl implements LocalService {

	@Override
	public void executeLocalService(String payload) {
		System.out.println("LocalService.executeLocalService(): 执行本地业务逻辑!");
	}
}
