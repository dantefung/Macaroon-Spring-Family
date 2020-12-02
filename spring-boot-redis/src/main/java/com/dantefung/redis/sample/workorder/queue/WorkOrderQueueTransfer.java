package com.dantefung.redis.sample.workorder.queue;

import com.alibaba.fastjson.JSONObject;
import com.dantefung.redis.plugin.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class WorkOrderQueueTransfer extends AbstractCacheManager {

	final static String ATOMIC_KEY = "delayed_queue_key_expire_lock_{0}";

	final static int ATOMIC_KEY_EXPIRE = 5000;

	@Autowired
	RedisService redisService;

	@Autowired
	WorkOrderQueueManager workOrderQueueManager;

	@Autowired
	WorkOrderCacheManager workOrderCacheManager;

	/**
	 * 从[挂起|暂存]队列转移到正式队列中
	 * @param cacheType 挂起|暂存
	 * @param delayedContext
	 * @return
	 */
	public Boolean transferImmediateQueue(WorkOrderCacheManager.CacheType cacheType, WorkOrderContext delayedContext) {
		boolean tryLock = false;
		Boolean done = Boolean.FALSE;
		String lockKey = null;
		try {
			WorkOrderContext.WorkOrder workOrder = delayedContext.getWorOrder();
			//lockKey = redisService.getString(MessageFormat.format(ATOMIC_KEY,workOrder.getWorkCode()));
			lockKey = MessageFormat.format(ATOMIC_KEY, workOrder.getWorkCode());
			tryLock = redisService.lock(lockKey, "2", ATOMIC_KEY_EXPIRE);
			if (tryLock) {
				//1、构建正式队列
				WorkOrderContext immediateContext = WorkOrderContext
						.buildImmediate(workOrder.getWorkCode(), workOrder.getPriority());
				done = workOrderQueueManager.leftPushIfAbsent(immediateContext);
				//2、从当前延迟队列移除该元素
				Long count = workOrderQueueManager.remove(delayedContext);
				log.info("[挂起|转存队remove],count={},delayedContext={}", count, JSONObject.toJSONString(delayedContext));
			}
		} catch (Exception e) {
			log.error("[transferImmediateQueue]异常,delayedContext={},cacheType={}",
					JSONObject.toJSONString(delayedContext), cacheType);
		} finally {
			if (Objects.nonNull(lockKey) && tryLock) {
				redisService.unlock(lockKey, "2");
			}
		}
		return Optional.ofNullable(done).orElse(Boolean.FALSE);
	}
}