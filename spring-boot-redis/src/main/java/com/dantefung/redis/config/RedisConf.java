package com.dantefung.redis.config;

import com.dantefung.redis.plugin.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConf {

	@Bean
	public RedisService redisService(@Autowired RedisConnectionFactory redisConnectionFactory) {
		// string redis template
		RedisTemplate<String, String> stringRedisTemplate = new RedisTemplate<>();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		stringRedisTemplate.setKeySerializer(stringRedisTemplate.getStringSerializer());
		stringRedisTemplate.setValueSerializer(stringRedisTemplate.getStringSerializer());
		stringRedisTemplate.setHashKeySerializer(stringRedisTemplate.getStringSerializer());
		stringRedisTemplate.setHashValueSerializer(stringRedisTemplate.getStringSerializer());
		stringRedisTemplate.afterPropertiesSet();
		// jdk redis template
		RedisTemplate<String, Object> jdkRedisTemplate = new RedisTemplate<>();
		jdkRedisTemplate.setConnectionFactory(redisConnectionFactory);
		jdkRedisTemplate.setKeySerializer(jdkRedisTemplate.getStringSerializer());
		jdkRedisTemplate.setValueSerializer(jdkRedisTemplate.getDefaultSerializer());
		jdkRedisTemplate.setHashKeySerializer(jdkRedisTemplate.getStringSerializer());
		jdkRedisTemplate.setHashValueSerializer(jdkRedisTemplate.getStringSerializer());
		jdkRedisTemplate.afterPropertiesSet();
		return new RedisService(stringRedisTemplate, jdkRedisTemplate);
	}

	@Configuration
	public class RedisConfig {
		@Bean
		RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory){
			RedisMessageListenerContainer container = new RedisMessageListenerContainer();
			container.setConnectionFactory(connectionFactory);
			return container;
		}
	}
}
