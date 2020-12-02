package com.dantefung.redis.sample.workorder.queue;

import com.alibaba.fastjson.JSONObject;
import com.dantefung.redis.sample.workorder.constant.CarthageConst;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;

@Component
@Slf4j
public class WorkOrderQueueManager extends AbstractCacheManager {

	final String LOCK_KEY = "ZSET_ATOMIC_LOCK";

	@Autowired
	WorkOrderCacheManager workOrderCacheManager;

	/**
	 * 从上下文队列类型获取队列redis key
	 * @param context
	 * @return
	 */
	String getRedisKey(WorkOrderContext context) {
		String keySuffix = null;
		switch (context.getQueueType()) {
			case immediate:
				keySuffix = CarthageConst.IMMEDIATE_QUEUE_DEFAULT;
				break;
			case stored:
				keySuffix = CarthageConst.STORED_QUEUE_DEFAULT;
				break;
			case suspended:
				keySuffix = CarthageConst.SUSPENDED_QUEUE_DEFAULT;
				break;
			default:
				break;
		}
		if (null != keySuffix) {
			if (context.isTest()) {
				keySuffix += CarthageConst.TEST_SUFFIX;
			}
			//return redisService.getString(keySuffix);
			return keySuffix;
		}
		return null;
	}

	/**
	 * 返回队列大小
	 * @param context
	 * @return
	 */
	public Long queueSize(WorkOrderContext context) {
		return redisService.zSetOperations().size(getRedisKey(context));
	}

	/**
	 * 执行处理（入队操作）
	 * @param context
	 * @return
	 */
	public Boolean leftPush(WorkOrderContext context) {
		String redisKey = getRedisKey(context);
		String workCode = context.getWorOrder().getWorkCode();
		double priority = context.getWorOrder().getPriority();
		Boolean action = redisService.zSetOperations().add(redisKey, workCode, priority);
		if (Objects.equals(Boolean.FALSE, action)) {
			Long value = redisService.zSetOperations().rank(redisKey, workCode);
			log.info("[Queue.leftPush],hasLeftPushed,action={},value={}, context={}", action, value,
					JSONObject.toJSON(context));
			if (Objects.nonNull(value)) {
				return Boolean.TRUE;
			}
		}
		log.info("[Queue.leftPush] context={}", JSONObject.toJSON(context));
		retry(MAX_RETRIES, context, idx -> action);
		return Optional.ofNullable(action).orElse(Boolean.FALSE);
	}

	/**
	 * 执行处理（入队操作）
	 * 如果入队元素缺席则入队，返回true；否则返回false。
	 * @param context
	 * @return
	 */
	public Boolean leftPushIfAbsent(WorkOrderContext context) {
		String redisKey = getRedisKey(context);
		String workCode = context.getWorOrder().getWorkCode();
		double priority = context.getWorOrder().getPriority();
		Boolean action = redisService.zSetOperations().add(redisKey, workCode, priority);
		log.info("[WorkOrderQueue.leftPushIfAbsent,action={},context={}", action, JSONObject.toJSON(context));
		return Optional.ofNullable(action).orElse(Boolean.FALSE);
	}

	/**
	 * 从队列移除某个元素
	 * @param context
	 * @return
	 */
	public Long remove(WorkOrderContext context) {
		String redisKey = getRedisKey(context);
		String workCode = context.getWorOrder().getWorkCode();
		log.info("[WorkOrderQueue.remove] context={}", JSONObject.toJSON(context));
		Long rem = redisService.zSetOperations().remove(redisKey, workCode);
		Long action = Optional.ofNullable(rem).orElse(0L);
		retry(MAX_RETRIES, context, idx -> action.longValue() > 0);
		return action;
	}

	/**
	 * 从集合中获取评分最小的成员出队
	 * @param context
	 * @return
	 */
	public WorkOrderContext.WorkOrder pop(WorkOrderContext context) {
		WorkOrderContext.WorkOrder workOrder = null;
		try {
			String redisKey = getRedisKey(context);
			//通过分布式锁，实现 zset 的 zpopmin 命令操作
			boolean locked = redisService.lock(LOCK_KEY, "1", 5000);
			if (locked) {
				//1、取出第一个最小评分元素
				Set<ZSetOperations.TypedTuple<String>> set = redisService.zrange(redisKey, 0, 0, null);
				if (set.isEmpty()) {
					return null;
				}
				//2、移除该最小评分元素
				Long value = redisService.removeZRange(redisKey, 0, 0);
				retry(MAX_RETRIES, context, idx -> value.longValue() > 0);
				//3、返回出队成员
				workOrder = WorkOrderContext.WorkOrder.builder().build();
				for (ZSetOperations.TypedTuple<String> each : set) {
					workOrder.setWorkCode(each.getValue());
					workOrder.setPriority(each.getScore());
					workOrder.setDelayedTime(0);
					break;
				}
			}
		} catch (Exception e) {
			log.error("[WorkOrderQueue.pop] exception ctx={}", JSONObject.toJSON(context));
		} finally {
			redisService.unlock(LOCK_KEY, "1");
		}
		return workOrder;
	}

	/**
	 * 按照升序查看队列中所有成员
	 * @param context
	 * @return
	 */
	public Set<WorkOrderContext.WorkOrder> rank(WorkOrderContext context) {

		Set<ZSetOperations.TypedTuple<Object>> set = redisService.zSetOperations()
				.rangeWithScores(getRedisKey(context), 0, -1);

		Set<WorkOrderContext.WorkOrder> members = Sets.newLinkedHashSetWithExpectedSize(set.size());

		set.forEach(each -> {
			WorkOrderContext.WorkOrder every = WorkOrderContext.WorkOrder.builder().workCode(each.getValue().toString())
					.priority(each.getScore())
					.delayedTime(getDelayedTime(context.getQueueType(), each.getValue().toString())).build();
			members.add(every);
		});
		return members;
	}

	/**
	 * 按照范围移除队列元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long removeRange(String key, long start, long end) {
		String redisKey = redisService.getString(key);
		Long count = redisService.zSetOperations().removeRange(redisKey, start, end);
		log.info("[WorkOrderQueue.removeRange] redisKey={},start={},end={},count={}", redisKey, start, end, count);
		return count;
	}

	/**
	 * 移除指定元素
	 * @param key
	 * @param values
	 * @return
	 */
	public Long removeValues(String key, List<Object> values) {
		String redisKey = redisService.getString(key);
		LongAdder longAdder = new LongAdder();
		values.forEach(each -> {
			Long count = redisService.zSetOperations().remove(redisKey, each);
			longAdder.add(count);
		});
		Long count = longAdder.longValue();
		log.info("[WorkOrderQueue.removeValues] redisKey={},values={},count={}", redisKey,
				JSONObject.toJSONString(values), count);
		return count;
	}

	/**
	 * 获取对应工单的延迟时间（适用于 挂起 和 转存）
	 * @param queueType
	 * @param workCode
	 * @return
	 */
	long getDelayedTime(WorkOrderContext.QueueType queueType, String workCode) {
		long delayedTime = 0;
		WorkOrderCacheManager.CacheType cacheType = null;
		switch (queueType) {
			case suspended:
				cacheType = WorkOrderCacheManager.CacheType.suspended_cache;
				break;
			case stored:
				cacheType = WorkOrderCacheManager.CacheType.stored_cache;
				break;
			default:
				break;
		}
		if (null != cacheType) {
			WorkOrderCacheManager.CacheValue cacheValue = workOrderCacheManager.get(cacheType, workCode);
			if (null != cacheValue) {
				delayedTime = cacheValue.getDelayedTime();
			}
		}
		return delayedTime;
	}
}