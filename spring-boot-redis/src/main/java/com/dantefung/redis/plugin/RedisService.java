package com.dantefung.redis.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.core.script.RedisScript;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisService {

	private RedisTemplate<String, String> stringRedisTemplate;

	private RedisTemplate<String, Object> jdkRedisTemplate;

	public RedisService(RedisTemplate<String, String> stringRedisTemplate,RedisTemplate<String, Object> jdkRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.jdkRedisTemplate = jdkRedisTemplate;
	}

	public void setStringRedisTemplate(RedisTemplate<String, String> stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void setJdkRedisTemplate(RedisTemplate<String, Object> jdkRedisTemplate) {
		this.jdkRedisTemplate = jdkRedisTemplate;
	}

	/**
	 * set k/v
	 * 
	 * @param key
	 * @param o
	 * @param timeout
	 */
	public void setObject(String key, Object o, long timeout) {
		if (o != null) {
			try {
				jdkRedisTemplate.opsForValue().set(key, o, timeout, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	public Object getObject(String key, Class<?> clazz) {
		try {
			Object value = jdkRedisTemplate.opsForValue().get(key);
			if (value == null) {
				return null;
			}
			return clazz.cast(value);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;

	}

	public int getInt(String key) {
		try {
			String value = stringRedisTemplate.opsForValue().get(key);
			if (StringUtils.isNotBlank(value)) {
				return Integer.parseInt(value);
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error("", e);
			return 0;
		}
	}

	public void setString(String key, String value, long timeout) {
		try {
			stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("", e);
		}

	}

	public String getString(String key) {
		try {
			return stringRedisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			log.error("", e);
			return null;
		}

	}

	public void setInt(String key, int value, long timeout) {
		try {
			stringRedisTemplate.opsForValue().set(key, String.valueOf(value), timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("", e);
		}

	}

	public void incr(String key, long timeout) {
		try {
			stringRedisTemplate.opsForValue().increment(key, 1L);
			stringRedisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("", e);
		}

	}

	public void delete(String key) {
		try {
			stringRedisTemplate.delete(key);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public List<String> hkeys(String key) {
		try {
			List<String> list = new ArrayList<>();
			Set<Object> keys = stringRedisTemplate.opsForHash().keys(key);
			for (Object obj : keys) {
				list.add(obj.toString());
			}
			return list;
		} catch (Exception e) {
			log.error("", e);
			return new ArrayList<>(0);
		}

	}

	public Map<String, String> hgetAll(String key) {
		try {
			Map<String, String> map = new HashMap<>();
			Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
			for (Map.Entry<Object, Object> entry : entries.entrySet()) {
				map.put(entry.getKey().toString(), entry.getValue().toString());
			}
			return map;
		} catch (Exception e) {
			log.error("", e);
			return new HashMap<>(0);
		}

	}

	public void hset(String key, String hashKey, String value) {
		try {
			stringRedisTemplate.opsForHash().put(key, hashKey, value);
		} catch (Exception e) {
			log.error("", e);
		}

	}
	public void hdel(String key, String hashKey) {
		try {
			stringRedisTemplate.opsForHash().delete(key, hashKey);
		} catch (Exception e) {
			log.error("", e);
		}

	}

	public String hget(String key, String hashKey) {
		try {
			Object str = stringRedisTemplate.opsForHash().get(key, hashKey);
			if(str!=null){
				return (String)str;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error("", e);
			return null;
		}

	}

	public List<String> multiGetString(List<String> keys) {
		try {
			return stringRedisTemplate.opsForValue().multiGet(keys);
		} catch (Exception e) {
			log.error("", e);
			return new ArrayList<>(0);
		}

	}

	public Map<String, Object> multiGetObject(List<String> keys, Class<?> cls) {
		try {
			List<Object> objs = jdkRedisTemplate.opsForValue().multiGet(keys);
			Map<String, Object> result = new HashMap<>();
			for (int i = 0; i < keys.size(); i++) {
				Object obj = objs.get(i);
				if (obj != null) {
					result.put(keys.get(i), cls.cast(obj));
				}
			}
			return result;
		} catch (Exception e) {
			log.error("", e);
			return new HashMap<>();
		}

	}
	public void multiSetObject(Map<String,Object> map,long timeout) {
		try {
			jdkRedisTemplate.opsForValue().multiSet(map);
			for(Map.Entry<String, Object> entry: map.entrySet()) {
				jdkRedisTemplate.expire(entry.getKey(), timeout, TimeUnit.MILLISECONDS);
			}
		} catch (Exception e) {
			log.error("", e);
		}

	}
	public void expire(String key, long timeout) {
		try {
			stringRedisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void publish(final String channel, final String message) {
		RedisCallback<Long> callback = input -> input.publish(stringRedisTemplate.getStringSerializer().serialize(channel), stringRedisTemplate.getStringSerializer().serialize(message));
		Long result = stringRedisTemplate.execute(callback);
		if(result==0L) {
			log.warn("一个订阅者都没有");
		}

	}

	/**
	 * zset添加一个成员
	 * @param key
	 * @param member
	 * @param score
	 */
	public void zset(String key,String member,double score){
		stringRedisTemplate.opsForZSet().add(key, member, score);
	}
	/**
	 * 追加score(key|member不存在的时候会新增)
	 * @param key
	 * @param member
	 * @param score
	 */
	public  void zincrby(String key,String member,double score){
		stringRedisTemplate.opsForZSet().incrementScore(key, member, score);
		
	}
	/**
	 * 分页获取排名（按score正序或倒序排列）
	 * @param key
	 * @param start
	 * @param limit
	 * @param order
	 * @return
	 */
	public Set<TypedTuple<String>> zrange(String key,long start,long limit,Order order ){
		if(order == Order.ASC){
			return stringRedisTemplate.opsForZSet().rangeWithScores(key, start, start+limit);
		}else{
			return stringRedisTemplate.opsForZSet().reverseRangeWithScores(key, start, start+limit);
		}
		
	}
	
	
	public String executeLua(RedisScript<String> script, List<String> keys, Object... args) {
		return stringRedisTemplate.execute(script, keys, args);
	}

	// 加锁脚本
	public static final String SCRIPT_LOCK = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return 1 else return 0 end";
	// 解锁脚本
	private static final String SCRIPT_UNLOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
	//redis实现的分布式锁 单位秒
	public boolean lock(String key, String value, int timeout) {
		Long result = stringRedisTemplate.execute(new RedisScript<Long>() {
			@Override
			public String getSha1() {
				return Sha1Util.encrypt(SCRIPT_LOCK);
			}
			@Override
			public Class<Long> getResultType() {
				return Long.class;
			}
			@Override
			public String getScriptAsString() {
				return SCRIPT_LOCK;
			}
		}, Collections.singletonList(key), value, String.valueOf(timeout));
		return result == 1L;
	}

	public boolean unlock(String key, String value) {
		Long result = stringRedisTemplate.execute(new RedisScript<Long>() {
			@Override
			public String getSha1() {
				return Sha1Util.encrypt(SCRIPT_UNLOCK);
			}
			@Override
			public Class<Long> getResultType() {
				return Long.class;
			}
			@Override
			public String getScriptAsString() {
				return SCRIPT_UNLOCK;
			}
		}, Collections.singletonList(key), value);
		return result == 1L;

	}
}