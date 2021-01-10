package com.dantefung.springbootcache.config;

import com.dantefung.springbootcache.customize.RedisEhCacheManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author DANTE FUNG
 * @date 2020-4-2 17:23:32
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {


	private static final int NO_PARAM_KEY = 0;
	private String keyPrefix = "CACHE_DEMO";// key前缀，用于区分不同项目的缓存，建议每个项目单独设置

	//key的生成
	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			char sp = ':';
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(keyPrefix);
			strBuilder.append(sp);
			// 类名
			strBuilder.append(target.getClass().getSimpleName());
			strBuilder.append(sp);
			// 方法名
			strBuilder.append(method.getName());
			strBuilder.append(sp);
			if (params.length > 0) {
				// 参数值
				for (Object object : params) {
					strBuilder.append(Optional.ofNullable(object).orElse(StringUtils.EMPTY).toString());
				}
			} else {
				strBuilder.append(NO_PARAM_KEY);
			}
			return strBuilder.toString();
		};
	}

	@Bean("ehCacheCacheManager")
	public CacheManager ehCacheCacheManager() {
		return new EhCacheCacheManager();
	}

	@Bean("redisCacheManager")
	public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(defaultCacheConfig())
				.withInitialCacheConfigurations(getCacheConfigurations()).transactionAware().build();
	}

/*	@Bean
	@Primary
	public CacheManager redisEhCacheManager(
			@Qualifier("ehCacheCacheManager") @Autowired CacheManager ehCacheCacheManager,
			@Qualifier("redisCacheManager") @Autowired CacheManager redisCacheManager) {
		return new RedisEhCacheManager((EhCacheCacheManager) ehCacheCacheManager,
				(RedisCacheManager) redisCacheManager);
	}*/

	@Bean
	@Primary
	public CacheManager redisEhCacheManager(
			@Qualifier("ehCacheCacheManager") @Autowired CacheManager ehCacheCacheManager,
			@Qualifier("redisCacheManager") @Autowired CacheManager redisCacheManager, RedisTemplate redisTemplate) {
		return new RedisEhCacheManager((EhCacheCacheManager) ehCacheCacheManager,
				(RedisCacheManager) redisCacheManager, redisTemplate);
	}

	private RedisCacheConfiguration defaultCacheConfig() {
		//  TTL  Time To Live
		/*前缀
		 * 过期时间
		 * key序列化
		 * value序列化
		 * */
		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30)).serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer()));

	}

	private RedisCacheConfiguration getCacheConfig(Duration ttl) {
		//  TTL  Time To Live
		/*前缀
		 * 过期时间
		 * key序列化
		 * value序列化
		 * */
		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(ttl).serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				// .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()));

	}

	private Map<String, RedisCacheConfiguration> getCacheConfigurations() {
		Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();

		//缓存键,且30秒后过期,30秒后再次调用方法时需要重新缓存
		configurationMap.put("expireKey", getCacheConfig(Duration.ofSeconds(30)));
		configurationMap.put("CACHE_EMPLOYEE_SCHEDULE_MODULE", getCacheConfig(Duration.ofMinutes(3)));
		configurationMap.put("MONTH_ATTENDANCE_STAT_MODULE", getCacheConfig(Duration.ofSeconds(5)));

		return configurationMap;
	}

	/**
	 * json序列化
	 * @return
	 */
	private RedisSerializer<Object> jackson2JsonRedisSerializer() {
		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		//json转对象类，不设置默认的会将json转成hashmap
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(mapper);
		return serializer;
	}
}
