/*
 * Copyright (C), 2015-2018
 * FileName: TestRedisMQController
 * Author:   DANTE FUNG
 * Date:     2020/4/29 15:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/29 15:13   V1.0.0
 */
package com.dantefung.redismq.controller;

import com.dantefung.redismq.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: TestRedisMQController
 * @Description: Redis 发布订阅测试.
 * @author DANTE FUNG
 * @date 2020/4/29 15:13
 */
@RestController
public class TestRedisMQController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@GetMapping("/public/message")
	public String publicMessage(@RequestParam("message") String message) {
		stringRedisTemplate.convertAndSend(Constant.CHANNEL, message);
		return "ok!";
	}
}
