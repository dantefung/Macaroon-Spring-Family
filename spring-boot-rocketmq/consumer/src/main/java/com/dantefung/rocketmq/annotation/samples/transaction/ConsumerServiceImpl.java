/*
 * Copyright (C), 2015-2018
 * FileName: ConsumerServiceImpl
 * Author:   DANTE FUNG
 * Date:     2020/4/14 19:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/14 19:31   V1.0.0
 */
package com.dantefung.rocketmq.annotation.samples.transaction;

import org.springframework.stereotype.Service;

/**
 * @Title: ConsumerServiceImpl
 * @Description:
 * @author DANTE FUNG
 * @date 2020/4/14 19:31
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Override
	public void readMessage(String message) {
		System.out.printf("ConsumerService.readMessage():读取消息%s\r\n", message);
	}
}
