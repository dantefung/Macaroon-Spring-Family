/*
 * Copyright (C), 2015-2020
 * FileName: TestController
 * Author:   DANTE FUNG
 * Date:     2020/12/2 9:44 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/2 9:44 下午   V1.0.0
 */
package com.dantefung.redis.demo;

import com.dantefung.redis.plugin.RedisService;
import com.dantefung.redis.sample.workorder.operation.OperateContext;
import com.dantefung.redis.sample.workorder.operation.OperateStrategyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @Title: TestController
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/02 21/44
 * @since JDK1.8
 */
@RestController
@RequestMapping
public class TestController {

	@Autowired
	private RedisService redisService;

	@Autowired
	private OperateStrategyManager operateStrategyManager;

	/**
	 * @Description: 测试监听key失效
	 * 业务场景：监听 Redis 键值对过期时间来实现订单自动关闭
	 * 在生成订单时，向 Redis 中增加一个 KV 键值对，K 为订单号，保证通过 K 能定位到数据库中的某个订单即可，V 可为任意值。
	 * 假设，生成订单时向 Redis 中存放 K 为订单号，V 也为订单号的键值对，并设置过期时间为 30 分钟，
	 * 如果该键值对在 30 分钟过期后能够发送给程序一个通知，或者执行一个方法，那么即可解决订单关闭问题。
	 * 实现：通过监听 Redis 提供的过期队列来实现，监听过期队列后，如果 Redis 中某一个 KV 键值对过期了，
	 * 那么将向监听者发送消息，监听者可以获取到该键值对的 K，注意，是获取不到 V 的，因为已经过期了，这就是上面所提到的，
	 * 为什么要保证能通过 K 来定位到订单，而 V 为任意值即可。拿到 K 后，通过 K 定位订单，并判断其状态，如果是未支付，更新为关闭，或者取消状态即可。
	 * @param :
	 * @Author: DANTE FUNG
	 * @Date: 2020/12/2 21:48
	 * @since JDK 1.8
	 * @return: java.lang.String
	 */
	@GetMapping("/key")
	public String keyExpiredTest() {
		String key = "2022019122800000000066112";
		redisService.setString(key, UUID.randomUUID().toString(), 1000);
		return "ok!";
	}

	@GetMapping("/suspend")
	public String suspend(@RequestParam(required = false, defaultValue = "1") int delay) {
		String workCode = "D2022019122800000000066112" + new Date().getTime();
		OperateContext operateContext = OperateContext.builder()
				.operateStrategyEnum(OperateContext.OperateStrategyEnum.SUSPEND_WORK_ORDER).operateParam(
						OperateContext.OperateParam.builder().workCode(workCode).delayedTime(new Date())
								.delayedSeconds(delay*1000).build()).build();
		operateStrategyManager.execute(operateContext);
		return "suspend!";
	}
}
