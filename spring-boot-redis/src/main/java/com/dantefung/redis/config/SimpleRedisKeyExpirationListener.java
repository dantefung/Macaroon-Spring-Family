package com.dantefung.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class SimpleRedisKeyExpirationListener extends KeyExpirationEventMessageListener {
 
    public SimpleRedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

	/**
	 * 所有监听key失效的应用实例都会收到该消息通知.
	 * 多进程抢占执行，需要考虑分布式锁.
	 * @param message
	 * @param pattern
	 */
	@Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
		try {
			String s = new String(message.getBody(), "UTF-8");
			log.info("body:{}", s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		log.info("expiredKey:{}", expiredKey);
    }
}