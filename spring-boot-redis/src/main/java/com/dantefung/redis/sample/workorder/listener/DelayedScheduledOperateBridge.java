package com.dantefung.redis.sample.workorder.listener;

import com.alibaba.fastjson.JSONObject;
import com.dantefung.redis.plugin.RedisService;
import com.dantefung.redis.sample.workorder.constant.CarthageConst;
import com.dantefung.redis.sample.workorder.operation.OperateContext;
import com.dantefung.redis.sample.workorder.operation.OperateStrategyManager;
import com.dantefung.redis.sample.workorder.queue.WorkOrderCacheManager;
import com.dantefung.redis.sample.workorder.queue.WorkOrderContext;
import com.dantefung.redis.sample.workorder.queue.WorkOrderQueueTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 该类的主要作用，就是通过WorkOrderQueueTransfer实现队列元素的转移，同时通过OperateStrategyManager实现工单的数据库表操作。
 */
@Slf4j
@Component
public class DelayedScheduledOperateBridge {

	static final String LOCK_KEY = CarthageConst.KEY_EXPIRE_LISTENER_LOCK;

	static final int EXPIRE_SECONDS = 120;

	@Autowired
	RedisService redisService;

	@Autowired
	WorkOrderQueueTransfer workOrderQueueTransfer;

	@Autowired
	OperateStrategyManager operateStrategyManager;

	/**
	 * 我们假定设置两个队列，一个队列维护正式工单，另一个队列维护挂起工单。
	 * 对于挂起操作，我们通过Redis设置key有效时间，
	 * 当key失效时，客户端监听失效事件，获取工单，实现 挂起工单队列的移除，正式队列的入队即可。
	 *
	 * 实现从延迟队列到正式队列的转移业务处理，同时更新工单的状态
	 * @param cacheType
	 * @param delayedContext
	 * @return
	 */
	public Boolean transferImmediateQueue(WorkOrderCacheManager.CacheType cacheType, WorkOrderContext delayedContext) {
		String workCode = delayedContext.getWorOrder().getWorkCode();
		boolean tryLock = false;
		String redisKey = null;
		try {
			//redisKey = redisService.getString(MessageFormat.format(LOCK_KEY, workCode));
			redisKey = MessageFormat.format(LOCK_KEY, workCode);
			tryLock = redisService.lock(redisKey, "1", EXPIRE_SECONDS);
			if (!tryLock) {
				log.info("[DelayedScheduledOperateBridge.tryLock={}获取锁失败,redisKey={}]挂起|转存既定时间处理幂等，workCode={}",
						tryLock, redisKey, workCode);
			}
			if (tryLock) {
				log.info("[DelayedScheduledOperateBridge.tryLock={}获取锁成功,redisKey={}]挂起|转存既定时间处理幂等，workCode={}",
						tryLock, redisKey, workCode);
				Boolean done = workOrderQueueTransfer.transferImmediateQueue(cacheType, delayedContext);
				if (!done.booleanValue()) {
					return Boolean.FALSE;
				}
				OperateContext operateContext = OperateContext.builder()
						.operateStrategyEnum(OperateContext.OperateStrategyEnum.DELAYED_SCHEDULED_ORDER)
						.operateParam(OperateContext.OperateParam.builder().workCode(workCode).build()).build();
				// 执行具体数据库处理策略..
				//operateStrategyManager.execute(operateContext);
				// 假设执行成功
				operateContext.buildExecuteResultWithSuccess();
				log.info("[DelayedScheduledOperateBridge.transferImmediateQueue],delayedContext={},callResult={}",
						JSONObject.toJSONString(delayedContext),
						JSONObject.toJSONString(operateContext.getExecuteResult()));
				return operateContext.getExecuteResult().isSuccess();
			}
		} catch (Exception e) {
			log.error("[DelayedScheduledOperateBridge]挂起|转存既定时间处理异常，workCode={},delayedContext={}", workCode,
					JSONObject.toJSONString(delayedContext), e);
		} finally {
			if (tryLock) {
				redisService.unlock(redisKey, "1");
				log.info("释放redis分布式锁成功!");
			}
		}
		return false;
	}
}