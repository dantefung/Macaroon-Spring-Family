package com.dantefung.springbootcache.facade;

import com.dantefung.springbootcache.sample.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Title: GenericConfigCacheableService
 * @Description: 配置项缓存管理器
 * @author DANTE FUNG
 * @date 2020/12/03 16/20
 * @since JDK1.8
 */
@Slf4j
@Component
public class GenericCacheableFacade {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private DB db;

	public <T> T getByKey(String key, Class<T> clazz) {
		// TODO: 防止缓存穿透, 要结合BitMap或布隆过滤器进行前置拦截过滤
		// 尝试读取缓存
		Cache cache = cacheManager.getCache("users");
		T t = (T) cache.get(key, clazz);
		log.info("尝试读取缓存:{} ...", t);
		if (Objects.nonNull(t)) {
			return t;
		}
		// 则读取数据库，写入缓存中
		log.info("查无缓存数据，从数据库读取...");
		// TODO: 加分布式锁，查询数据库更新缓存，其他线程采取重试策略，这样数据库不会同时受到很多线程访问同一条数据
		// 模拟读取数据库
		Object value = db.get(key);

		// 基础写法: 防止缓存空值
//		if (Objects.nonNull(value)) {
//			// 更新回缓存
//			cache.put(key, value);
//			t = (T) value;
//		}

		// 升级版写法.
		cache.put(key, value);
		// 防止缓存穿透, 要缓存空值, 有短暂的数据不一致问题.
		if (Objects.isNull(value)) {
			// 更新回缓存
			cache.put(key, new Object());
			// TODO: 针对空数据设置过期时间
			t = (T) value;
		}
		return t;
	}

	public void evict(String key) {
		Cache cache = cacheManager.getCache("users");
		cache.evict(key);
	}
}
