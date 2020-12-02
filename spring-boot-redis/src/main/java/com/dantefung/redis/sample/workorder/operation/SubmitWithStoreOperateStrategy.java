package com.dantefung.redis.sample.workorder.operation;

import com.dantefung.redis.sample.workorder.entity.WorkOrder;
import com.dantefung.redis.sample.workorder.queue.WorkOrderCacheManager;
import com.dantefung.redis.sample.workorder.queue.WorkOrderContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Objects;

@Slf4j
@Component
public class SubmitWithStoreOperateStrategy extends AbstractSubmitOperateStrategy{
    /**
     * 转存天数 换算 秒数
     */
    static final int DAY_TO_SECONDS = 24 * 60 * 60;

    @Override
    public void prepare(OperateContext context) {

    }

    @Override
    public boolean paramCheck(OperateContext context) {

        return false;
    }

    @Override
	WorkOrder buildWorkOrder(OperateContext context){

        return null;
    }

	@Override
	void operationExtend(OperateContext context) {

	}

	@Override
    public void setDelayedTime(OperateContext context) {

    }
}