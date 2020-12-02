package com.dantefung.redis.sample.workorder.queue;

import com.alibaba.fastjson.JSONObject;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 该类的主要作用，基于Redis String对象存储，实现具有Key失效机制的存储。
 * 内部静态类CacheValue，作为 Redis String 对象存储的 Value 值。
 * 内部枚举类CacheType，维护了缓存 Key 的业务前缀。
 * 特别说明的是，我们构成 Redis String 存储 Key 的命名规则，例如：carCarthage:stored_cache_单号。
 */
@Component
@Slf4j
public class WorkOrderCacheManager extends AbstractCacheManager {

	/**
	 * 设置缓存并设置缓存失效日期
	 * @param cache
	 */
	public void setCacheInExpire(CacheValue cache) {
		retry(MAX_RETRIES, cache, idx -> {
			//String redisKey = redisService.getString(getRedisKeySuffix(cache.getType(), cache.getWorkCode()));
			String redisKey = getRedisKeySuffix(cache.getType(), cache.getWorkCode());
			redisService.setString(redisKey, JSONObject.toJSONString(cache), cache.getExpireSeconds());
			log.info("[setCacheInExpire],redisKey={},CacheValue={}", redisKey, JSONObject.toJSONString(cache));
			return Boolean.TRUE;
		});
	}

	/**
	 * 查询某个工单号的缓存值
	 * @param cacheType 缓存类型 {@link CacheType}
	 * @param workCode 工单号
	 * @return
	 */
	public CacheValue get(CacheType cacheType, String workCode) {
		String redisKey = redisService.getString(getRedisKeySuffix(cacheType, workCode));
		String value = redisService.getString(redisKey);
		return JSONObject.parseObject(value, CacheValue.class);
	}

	/**
	 * 从上下文队列类型获取队列redis key
	 * @param cacheType 缓存类型 {@link CacheType}
	 * @param workCode 工单号
	 * @return
	 */
	String getRedisKeySuffix(CacheType cacheType, String workCode) {
		switch (cacheType) {
			case stored_cache:
				return CacheType.stored_cache.getKey() + workCode;
			case suspended_cache:
				return CacheType.suspended_cache.getKey() + workCode;
			default:
				break;
		}
		return null;
	}

	/**
	 * 缓存值
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CacheValue {
		/**
		 * 缓存类型
		 */
		private CacheType type;
		/**
		 * 工单号
		 */
		private String workCode;
		/**
		 * 优先级
		 */
		private double priority;
		/**
		 * 延迟截止时间(单位：时间戳)
		 */
		private long delayedTime;
		/**
		 * 缓存失效时间(单位：秒)
		 */
		private long expireSeconds;

		/**
		 * 创建-挂起队列（挂起n小时执行）
		 *
		 * @param workCode
		 * @param priority
		 * @param delayedTime
		 * @param expireSeconds
		 * @return
		 */
		public static CacheValue buildSuspended(String workCode, double priority, long delayedTime,
				long expireSeconds) {
			return CacheValue.builder().type(CacheType.suspended_cache).workCode(workCode).priority(priority)
					.delayedTime(delayedTime).expireSeconds(expireSeconds).build();
		}

		/**
		 * 转存队列（转存n天后执行）
		 *
		 * @param workCode
		 * @param priority
		 * @param delayedTime
		 * @param expireSeconds
		 * @return
		 */
		public static CacheValue buildStored(String workCode, double priority, long delayedTime, long expireSeconds) {
			return CacheValue.builder().type(CacheType.stored_cache).workCode(workCode).priority(priority)
					.delayedTime(delayedTime).expireSeconds(expireSeconds).build();
		}
	}

	/**
	 * 实现 挂起|转存 缓存key
	 */
	@Getter
	public enum CacheType {
		stored_cache("stored_cache_"), suspended_cache("suspended_cache_"),
		;

		CacheType(String key) {
			this.key = key;
		}

		private String key;
	}
}