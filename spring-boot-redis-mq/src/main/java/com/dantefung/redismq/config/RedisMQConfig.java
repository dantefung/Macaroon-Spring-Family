package com.dantefung.redismq.config;

import com.dantefung.redismq.annotation.EnableRedisMQ;
import com.dantefung.redismq.common.Constant;
import com.dantefung.redismq.receiver.DefaultRedisReceiver;
import com.dantefung.redismq.receiver.RedisReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

/**
 * @author DANTE FUNG
 * redis管理websocket配置，利用redis的发布订阅功能实现，具备集群功能
 * 可以扩展此类，添加listener和topic及相应的receiver，使用自己的Enable注解导入即可
 * @see EnableRedisMQ
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = {"configuration.switch.redis-mq"}, matchIfMissing = true)// 条件装配
public class RedisMQConfig {

	public RedisMQConfig() {
		log.info("RedisMQConfig正在被初始化....");
	}

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean("receiver")
    public RedisReceiver receiver(
            @Autowired@Qualifier("latch") CountDownLatch latch) {
        return new DefaultRedisReceiver(latch);
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(@Qualifier("receiver") RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, RedisReceiver.RECEIVER_METHOD_NAME);
    }

    @Bean("redisMessageListenerContainer")
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(Constant.CHANNEL));
        return container;
    }

}