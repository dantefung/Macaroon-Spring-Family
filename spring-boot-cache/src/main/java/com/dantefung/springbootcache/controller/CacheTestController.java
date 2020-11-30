/*
 * Copyright (C), 2015-2020
 * FileName: CacheTestController
 * Author:   DANTE FUNG
 * Date:     2020/11/25 14:27
 * Description: 缓存测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/25 14:27   V1.0.0
 */
package com.dantefung.springbootcache.controller;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Title: CacheTestController
 * @Description: 缓存测试
 * @author DANTE FUNG
 * @date 2020/11/25 14/27
 * @since JDK1.8
 */
@Slf4j
@RestController
@RequestMapping("/test/cache")
public class CacheTestController {

	@Autowired
	private CacheManager cacheManager;

	@GetMapping("/load")
	@Cacheable(value = "users")
	public Object load() {
		return new Date().getSeconds();
	}

	@GetMapping("/program")
	public Object program() {
		Collection<String> cacheNames = cacheManager.getCacheNames();
		List<String> cacheNameList = new ArrayList<>(cacheNames);
		StringBuilder sb = new StringBuilder("读取的缓存列表为:");
		for (int i = 0; i < cacheNameList.size(); i++) {
			sb.append("\r\n--" + (i + 1) + " " + cacheNameList.get(i) + "\r\n");
		}
		log.info(sb.toString());

		Cache cache = cacheManager.getCache("users");
		cache.put("zhansan", "张三");

		String value = cache.get("zhansan", String.class);
		log.info("read from cache: {}", value);


		EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) cacheManager;
		System.out.println(ehCacheCacheManager);

		log.info("read from cache: {}", value);

		return "ok!";
	}


}
