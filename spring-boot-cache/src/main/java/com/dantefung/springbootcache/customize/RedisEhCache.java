package com.dantefung.springbootcache.customize;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class RedisEhCache extends AbstractValueAdaptingCache {

    private EhCacheCacheManager ehCacheCacheManager;
    private RedisCacheManager redisCacheManager;
    private RedisTemplate redisTemplate;
    private String name;

	/**
	 * Create new {@link RedisEhCache}.
	 *
	 * @param name must not be {@literal null}.
	 * @param ehCacheCacheManager must not be {@literal null}.
	 * @param redisCacheManager must not be {@literal null}.
	 */
	protected RedisEhCache(String name, EhCacheCacheManager ehCacheCacheManager, RedisCacheManager redisCacheManager) {
		super(true);

		Assert.notNull(name, "Name must not be null!");
		Assert.notNull(ehCacheCacheManager, "EhCacheCacheManager must not be null!");
		Assert.notNull(redisCacheManager, "RedisCacheManager must not be null!");

		this.name = name;
		this.ehCacheCacheManager = ehCacheCacheManager;
		this.redisCacheManager = redisCacheManager;
	}

	/**
	 * Create new {@link RedisEhCache}.
	 *
	 * @param name must not be {@literal null}.
	 * @param ehCacheCacheManager must not be {@literal null}.
	 * @param redisCacheManager must not be {@literal null}.
	 */
	protected RedisEhCache(String name, EhCacheCacheManager ehCacheCacheManager, RedisCacheManager redisCacheManager, RedisTemplate redisTemplate) {
		super(true);

		Assert.notNull(name, "Name must not be null!");
		Assert.notNull(ehCacheCacheManager, "EhCacheCacheManager must not be null!");
		Assert.notNull(redisCacheManager, "RedisCacheManager must not be null!");
		Assert.notNull(redisTemplate, "RedisTemplate must not be null!");

		this.name = name;
		this.ehCacheCacheManager = ehCacheCacheManager;
		this.redisCacheManager = redisCacheManager;
		this.redisTemplate = redisTemplate;
	}

	@Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache && null != ehCache.get(key)) {
            log.trace("select from ehcache,key:{}", key);
            return ehCache.get(key);
        }

        Cache redisCache = redisCacheManager.getCache(this.name);
        if (null != redisCache && null != redisCache.get(key)) {
            log.trace("select from redis,key:{}", key);
            if (ehCache != null) {
                ehCache.put(key, redisCache.get(key).get());
            }
            return redisCache.get(key);
        }

        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache && null != ehCache.get(key, type)) {
            log.trace("select from ehcache,key:{},type:{}", key, type);
            return ehCache.get(key, type);
        }

        Cache redisCache = redisCacheManager.getCache(this.name);
        if (null != redisCache && null != redisCache.get(key, type)) {
            log.trace("select from redis,key:{},type:{}", key, type);
            ehCache.put(key, redisCache.get(key).get());
            return redisCache.get(key, type);
        }

        return null;
    }

	@Override
	protected Object lookup(Object key) {
		return null;
	}

	@Override
    public <T> T get(Object key, Callable<T> valueLoader) {
		// 先从本地缓存读取
        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache && null != ehCache.get(key, valueLoader)) {
            log.trace("select from ehcache,key:{},valueLoader:{}", key, valueLoader);
            return ehCache.get(key, valueLoader);
        }
		// 再从分布式缓存中读取
        Cache redisCache = redisCacheManager.getCache(this.name);
        if (null != redisCache && null != redisCache.get(key, valueLoader)) {
            log.trace("select from redis,key:{},valueLoader:{}", key, valueLoader);
            ehCache.put(key, redisCache.get(key).get());
            return redisCache.get(key, valueLoader);
        }

        return null;
    }

    @Override
    public void put(Object key, Object value) {

		if (!super.isAllowNullValues() && value == null) {
			this.evict(key);
			return;
		}

		Cache redisCache = redisCacheManager.getCache(this.name);
		if (null != redisCache) {
			log.trace("insert into redis,key:{},value:{}", key, value);
			redisCache.put(key, value);
		}

		// TODO: 缓存变更时通知其他节点清理本地缓存. 使用Redis的发布订阅

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache) {
        	// 兼容redis的key的格式.
            log.trace("insert into ehcache,key:{},value:{}", key, value);
			key = String.format("%s::%s", this.name, key);
            ehCache.put(key, value);
        }

    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {

		ValueWrapper valueWrapper = null;
		synchronized (key) {
			Cache redisCache = redisCacheManager.getCache(this.name);
			if (null != redisCache) {
				log.trace("insert into redis,key:{},value:{}", key, value);
				valueWrapper = redisCache.putIfAbsent(key, value);
			}

			// TODO: 缓存变更时通知其他节点清理本地缓存. 使用Redis的发布订阅

			Cache ehCache = ehCacheCacheManager.getCache(this.name);
			if (null != ehCache) {
				log.trace("insert into ehcache,key:{},value:{}", key, value);
				valueWrapper = ehCache.putIfAbsent(key, value);
			}
		}
        return valueWrapper;
    }

    @Override
    public void evict(Object key) {
		// 先清除redis中缓存数据，然后清除ehcache中的缓存，
		// 避免短时间内如果先清除ehcache缓存后其他请求会再从redis里加载
		Cache redisCache = redisCacheManager.getCache(this.name);
		if (null != redisCache) {
			log.trace("delete from redis,key:{}", key);
			redisCache.evict(key);
		}

		// TODO: 缓存变更时通知其他节点清理本地缓存. 使用Redis的发布订阅


        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache) {
            log.trace("delete from ehcache,key:{}", key);
            ehCache.evict(key);
        }
    }

    @Override
    public void clear() {

		// 先清除redis中缓存数据，然后清除ehcache中的缓存，
		// 避免短时间内如果先清除ehcache缓存后其他请求会再从redis里加载
		Cache redisCache = redisCacheManager.getCache(this.name);
		if (null != redisCache) {
			log.info("clear redis");
			redisCache.clear();
		}

		// TODO: 缓存变更时通知其他节点清理本地缓存. 使用Redis的发布订阅

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if (null != ehCache) {
            log.info("clear ehcache");
            ehCache.clear();
        }
    }

	/**
	 * @Description: 清理本地缓存
	 * @param key:
	 * @Author: DANTE FUNG
	 * @Date: 2021/1/10 20:49
	 * @since JDK 1.8
	 * @return: void
	 */
	public void clearLocal(Object key) {
		log.debug("clear local cache, the key is : {}", key);
		Cache ehCache = ehCacheCacheManager.getCache(this.name);
		if (null != ehCache) {
			log.info("clear ehcache");
			ehCache.clear();
		}
	}

	/**
	 * 使用这个方法的话，必须注入redisTemplate
	 * @param cacheName
	 * @return
	 */
	public List<Object> keys(String cacheName) {
		Assert.notNull(redisTemplate, "RedisTemplate must not be null! Please use another RedisEhManager constructor to init it!");
		EhCacheCache ehCache = (EhCacheCache) ehCacheCacheManager.getCache(cacheName);
		Ehcache ehCacheNativeCache = ehCache.getNativeCache();
		List keys = ehCacheNativeCache.getKeys();
		List redisKeys = new ArrayList<>(redisTemplate.keys(cacheName + "*"));
		log.trace("keys from ehCache:{} , redis :{}", keys, redisKeys);
		keys.addAll(redisKeys);
		return keys;
	}
}
