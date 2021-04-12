package com.dantefung.core.cache;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作
 *
 * @author 陈章伟
 * @time 2017年9月22日
 */
public class RedisKvOperate implements KvOperate {
	private RedisTemplate<String, Object> redisTemplate;
	/** Spring Application Context */
	private static ApplicationContext appContext;

	/**
	 * 根据配置文件的集群开关，判断是否使用集群
	 *
	 * @author 陈章伟
	 * @time 2017年9月22日
	 */
	@SuppressWarnings("unchecked")
	public RedisKvOperate(String clusterOn) {
		if (clusterOn != null && clusterOn.equalsIgnoreCase("off")) {
			redisTemplate = (RedisTemplate<String, Object>) appContext.getBean("redisTemplate");
		} else {
			redisTemplate = (RedisTemplate<String, Object>) appContext.getBean("redisClusterTemplate");
		}
	}

	@Override
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void set(String key, Object value, int timeoutSecond) {
		redisTemplate.opsForValue().set(key, value, timeoutSecond, TimeUnit.SECONDS);
	}

	@Override
	public void expire(String key, int timeoutSecond) {
		redisTemplate.expire(key, timeoutSecond, TimeUnit.SECONDS);
	}

	@Override
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public long inc(String key) {
		return incBy(key, 1);
	}

	@Override
	public long incBy(String key, long step) {
		return redisTemplate.opsForValue().increment(key, step);
	}

	@Override
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 根据前缀删除所有key
	 * @author 陈章伟
	 * @time 2017年11月9日上午10:50:57
	 */
	@Override
	public void delKeysByPrefix(String keyPrefix) {
		Set<String> keys = keys(keyPrefix);
		for (String key : keys) {
			del(key);
		}
	}

	/**
	 * 根据keys获取所有的value,返回的顺序和keys一致
	 * Damon
	 */
	@Override
	public List<Object> multiGet(Collection<String> keys) {
		return redisTemplate.opsForValue().multiGet(keys);
	}

	@Override
	public long lpush(String key, Object value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

	@Override
	public Object lpop(String key) {
		return key == null ? null : redisTemplate.opsForList().leftPop(key);
	}

	@Override
	public List<Object> range(String key, long start, long end) {
		return key == null ? null : redisTemplate.opsForList().range(key, start, end);
	}

	// ******************************** hash 操作 *************************************************

	@Override
	public boolean hhasKey(String key, Object hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	@Override
	public Object hget(String key, Object hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	@Override
	public Set<Object> hkeys(String key) {
		return redisTemplate.opsForHash().keys(key);
	}

	@Override
	public Map<Object, Object> hentries(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	@Override
	public void hput(String key, Object hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	@Override
	public long hdelete(String key, Object... hashKeys) {
		return redisTemplate.opsForHash().delete(key, hashKeys);
	}

	// ******************************** hash 操作 *************************************************
}
