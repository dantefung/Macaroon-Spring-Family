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
		// 尝试读取缓存
		Cache cache = cacheManager.getCache("users");
		T t = (T) cache.get(key, clazz);
		log.info("尝试读取缓存:{} ...", t);
		if (Objects.isNull(t)) {
			// 则读取数据库，写入缓存中
			log.info("查无缓存数据，从数据库读取...");
			// 模拟读取数据库
			Object value = db.get(key);
			// 更新回缓存
			cache.put(key, value);
			t = (T) value;
		}
		return t;
	}

	public void evict(String key) {
		Cache cache = cacheManager.getCache("users");
		cache.evict(key);
	}
}
