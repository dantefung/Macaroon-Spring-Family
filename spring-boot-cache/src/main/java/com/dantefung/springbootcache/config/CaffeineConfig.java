package com.dantefung.springbootcache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching //开启缓存
public class CaffeineConfig {
	public static final int DEFAULT_MAXSIZE = 10000;
	public static final int DEFAULT_TTL = 600;

	/**
	 * 定义cache名称、超时时长（秒）、最大容量
	 * 每个cache缺省：10秒超时、最多缓存50000条数据，需要修改可以在构造方法的参数中指定。
	 */
	public enum Caches {
		getUserById(600),          //有效期600秒
		listCustomers(7200, 1000),  //有效期2个小时 , 最大容量1000
		;

		Caches() {
		}

		Caches(int ttl) {
			this.ttl = ttl;
		}

		Caches(int ttl, int maxSize) {
			this.ttl = ttl;
			this.maxSize = maxSize;
		}

		private int maxSize = DEFAULT_MAXSIZE;    //最大數量
		private int ttl = DEFAULT_TTL;        //过期时间（秒）

		public int getMaxSize() {
			return maxSize;
		}

		public int getTtl() {
			return ttl;
		}
	}

	/**
	 * 创建基于Caffeine的Cache Manager
	 * @return
	 */
	@Bean("caffeineCacheManager")
	public CacheManager caffeineCacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		List<CaffeineCache> caches = new ArrayList<CaffeineCache>();
		for (Caches c : Caches.values()) {
			caches.add(new CaffeineCache(c.name(),
					Caffeine.newBuilder().recordStats().expireAfterWrite(c.getTtl(), TimeUnit.SECONDS)
							.maximumSize(c.getMaxSize()).build()));
		}

		cacheManager.setCaches(caches);
		return cacheManager;
	}
}
