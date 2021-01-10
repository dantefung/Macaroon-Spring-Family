package com.dantefung.springbootcache.customize;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: DANTE FUNG
 * @Date: 2021/1/10 20:41
 * @since JDK 1.8
 */
@Slf4j
@Data
public class RedisEhCacheManager implements CacheManager {
	private EhCacheCacheManager ehCacheCacheManager;
	private RedisCacheManager redisCacheManager;
	private RedisTemplate redisTemplate;
	private Map<String, RedisEhCache> cacheMap = new ConcurrentHashMap<>();

	private RedisEhCacheManager() {

	}

	public RedisEhCacheManager(EhCacheCacheManager ehCacheCacheManager, RedisCacheManager redisCacheManager) {
		this.ehCacheCacheManager = ehCacheCacheManager;
		this.redisCacheManager = redisCacheManager;
	}

	public RedisEhCacheManager(EhCacheCacheManager ehCacheCacheManager, RedisCacheManager redisCacheManager,
			RedisTemplate redisTemplate) {
		this.ehCacheCacheManager = ehCacheCacheManager;
		this.redisCacheManager = redisCacheManager;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Cache getCache(String name) {
		return cacheMap.computeIfAbsent(name, (key) -> {
			if (null != redisTemplate) {
				return new RedisEhCache(name, ehCacheCacheManager, redisCacheManager, redisTemplate);
			}
			return new RedisEhCache(name, ehCacheCacheManager, redisCacheManager);
		});
	}

	@Override
	public Collection<String> getCacheNames() {
		Collection<String> cacheNamesInEhCache = ehCacheCacheManager.getCacheNames();
		Collection<String> cacheNamesInRedis = redisCacheManager.getCacheNames();
		log.info(">>>>>> cacheNamesInEhCache : {} cacheNamesInRedis: {}", cacheNamesInEhCache, cacheNamesInRedis);
		return Stream.concat(cacheNamesInEhCache.stream(), cacheNamesInRedis.stream()).collect(Collectors.toList());
	}

	public void clearLocal(String cacheName, Object key) {
		Cache cache = cacheMap.get(cacheName);
		if (cache == null) {
			return;
		}

		RedisEhCache redisCaffeineCache = (RedisEhCache) cache;
		redisCaffeineCache.clearLocal(key);
	}
}
