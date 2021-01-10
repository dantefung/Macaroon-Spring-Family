/*
 * Copyright (C), 2015-2020
 * FileName: CacheDataInitializer
 * Author:   DANTE FUNG
 * Date:     2021/1/8 20:41
 * Description: 缓存数据初始化器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/1/8 20:41   V1.0.0
 */
package com.dantefung.springbootcache.config;

import com.dantefung.springbootcache.sample.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

/**
 * @Title: CacheDataInitializer
 * @Description: 缓存数据初始化器
 * @author DANTE FUNG
 * @date 2021/01/08 20/41
 * @since JDK1.8
 */
@Slf4j
@Component
public class CacheDataInitializer {

	@Autowired
	private DB db;
	@Autowired
	private CacheManager cacheManager;

	@PostConstruct
	public void init() {
		// 模拟从数据库获取数据加载至本地缓存
		log.info("cache data init ,cacheManager: {}...", cacheManager.toString());
		Cache cache = cacheManager.getCache("users");
		Set<Map.Entry<String, String>> set = db.entrySet();
		set.stream().forEach(entry -> cache.put(entry.getKey(), entry.getValue()));
	}
}
