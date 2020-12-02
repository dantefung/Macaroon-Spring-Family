package com.dantefung.redis.sample.workorder.listener;

import com.dantefung.redis.plugin.RedisService;
import com.dantefung.redis.sample.workorder.entity.WorkOrder;
import com.dantefung.redis.sample.workorder.queue.WorkOrderCacheManager;
import com.dantefung.redis.sample.workorder.queue.WorkOrderContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    final static String STORED_CACHE_KEY_PREFIX = WorkOrderCacheManager.CacheType.stored_cache.getKey();

    final static String SUSPENDED_CACHE_KEY_PREFIX = WorkOrderCacheManager.CacheType.suspended_cache.getKey();

    @Autowired
	RedisService redisService;

    @Autowired
    DelayedScheduledOperateBridge delayedScheduledOperateBridge;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
    	// TODO: 执行业务操作...
		log.info("message:{}", message);
		Date startTime = new Date();
		String expiredKey = message.toString();
		String bizPrefix = "suspended_cache_";
		if(!expiredKey.startsWith(bizPrefix)){
			return;
		}
		String caseOfStored = STORED_CACHE_KEY_PREFIX;
		String caseOfSuspended = SUSPENDED_CACHE_KEY_PREFIX;
		// 放入正式派单队列
		WorkOrderCacheManager.CacheType cacheType;
		WorkOrderContext.QueueType queueType;
		if(expiredKey.startsWith(caseOfStored)){
			queueType = WorkOrderContext.QueueType.stored;
			cacheType = WorkOrderCacheManager.CacheType.stored_cache;
		}else if(expiredKey.startsWith(caseOfSuspended)){
			queueType = WorkOrderContext.QueueType.suspended;
			cacheType = WorkOrderCacheManager.CacheType.suspended_cache;
		}else{
			return;
		}
		String workCode = getWorkCode(expiredKey);
		log.info("监听到 redis key=[{}] 已过期",expiredKey);
		if(Objects.nonNull(workCode)) {
			log.info("监听到 redis key=[{}],挂起|转存工单开始处理，workCode={}", expiredKey, workCode);
			// TODO: 业务操作.
			// 假设查询数据库得到workOrder
			WorkOrder workOrder = new WorkOrder();
			workOrder.setWorkCode(workCode);
			workOrder.setCasePriority(1);
			WorkOrderContext delayedContext = WorkOrderContext.builder()
					.worOrder(WorkOrderContext.WorkOrder.builder().delayedTime(5).priority(workOrder.getCasePriority()).workCode(workOrder.getWorkCode()).build())
					.queueType(queueType). build();
			// 实现从延迟队列转移到正式队列.
			Boolean done = delayedScheduledOperateBridge.transferImmediateQueue(cacheType,delayedContext);
		}
    }

	/**
	 * 从字符串截取制定的工单号
	 * @param value
	 * @return
	 */
	String getWorkCode(String value){
		return value.substring(value.lastIndexOf("_") + 1);
	}

}