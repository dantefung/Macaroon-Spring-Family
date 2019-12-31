package com.dantefung.redis.demo;

import com.dantefung.redis.plugin.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class DistributeLockDemo {

	@Autowired
	private RedisService redisService;

	@RequestMapping("/dsLockDemo")
	public String dsLockDemo() {
		String orderNo = "2022019122800000000066112";
		boolean lock = redisService.lock(orderNo, "lock", 2);
		if (lock) {
			return "locked!please try again later!";
		}
		try {
			// do biz logic

		} catch (Exception ex) {
			log.error(ex.getMessage());
		} finally {
			redisService.unlock(orderNo, "lock");
		}

		return "ok!";
	}
}
