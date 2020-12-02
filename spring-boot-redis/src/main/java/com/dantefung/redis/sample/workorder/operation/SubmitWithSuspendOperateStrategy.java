package com.dantefung.redis.sample.workorder.operation;

import com.dantefung.redis.sample.workorder.entity.WorkOrder;
import com.dantefung.redis.sample.workorder.queue.WorkOrderCacheManager;
import com.dantefung.redis.sample.workorder.queue.WorkOrderContext;
import com.dantefung.redis.sample.workorder.queue.WorkOrderQueueManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class SubmitWithSuspendOperateStrategy extends AbstractSubmitOperateStrategy {
	/**
	 * 转存天数 换算 秒数
	 */
	static final int DAY_TO_SECONDS = 24 * 60 * 60;

	@Autowired
	private WorkOrderQueueManager workOrderQueueManager;
	@Autowired
	private WorkOrderCacheManager workOrderCacheManager;

	@Override
	public void prepare(OperateContext context) {
		log.info("-----> prepare ...");
	}

	@Override
	public boolean paramCheck(OperateContext context) {
		log.info("-----> paramCheck ...");
		// 假定参数检查都成功
		return super.paramCheck(context);
	}

	@Override
	WorkOrder buildWorkOrder(OperateContext context) {
		log.info("-----> buildWorkOrder ...");
		WorkOrder workOrder = super.buildWorkOrder(context);
		workOrder.setMainStatus(2);
		workOrder.setSubStatus(1);
		workOrder.setFinished(false);
		workOrder.setStore(false);
		//setSuspendedCount 这里需要重置为0，转存后派单流程状态依赖该字段
		workOrder.setSuspendedCount(0);
		workOrder.setDelayedTime(context.getOperateParam().getDelayedTime());
		return workOrder;
	}

	@Override
	void operationExtend(OperateContext context) {
		log.info("-----> operationExtend ...");
		long delayedTime = context.getOperateParam().getDelayedTime().getTime();
		int delayedSeconds = context.getOperateParam().getDelayedSeconds();
		WorkOrder workOrder = context.getWorkOrder();
		WorkOrderContext cxt = WorkOrderContext
				.buildSuspended(workOrder.getWorkCode(), workOrder.getCasePriority(), delayedTime);
		workOrderQueueManager.leftPush(cxt);
		WorkOrderCacheManager.CacheValue cacheValue = WorkOrderCacheManager.CacheValue.
				buildSuspended(workOrder.getWorkCode(), workOrder.getCasePriority(), delayedTime, delayedSeconds);
		workOrderCacheManager.setCacheInExpire(cacheValue);
		super.operationExtend(context);
	}

	@Override
	public void setDelayedTime(OperateContext context) {
		log.info("-----> setDelayedTime ...");
		/*Date delayedTime = new Date();
		context.getOperateParam().setDelayedSeconds(1 * DAY_TO_SECONDS);*/
		context.getOperateParam().setDelayedSeconds(context.getOperateParam().getDelayedSeconds());
	}
}