package com.dantefung.redismq.receiver;

import com.dantefung.redismq.common.Constant;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;


/**
 * redis消息订阅者
 * @author DANTE FUNG
 */
@Slf4j
public class DefaultRedisReceiver implements RedisReceiver {

	private CountDownLatch latch;

	public DefaultRedisReceiver(CountDownLatch latch) {
		this.latch = latch;
	}

	/**
	 * 此方法会被反射调用
	 */
	@Override
	public void receiveMessage(String message) {
		log.info("=======================receiveMessage========================");
		log.info("接收到来自{}频道的消息:{}", Constant.CHANNEL, message);
		latch.countDown();
	}
}