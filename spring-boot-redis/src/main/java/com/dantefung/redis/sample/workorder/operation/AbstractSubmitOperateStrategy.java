/*
 * Copyright (C), 2015-2020
 * FileName: AbstractSubmitOperateStrategy
 * Author:   DANTE FUNG
 * Date:     2020/12/2 10:44 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/2 10:44 下午   V1.0.0
 */
package com.dantefung.redis.sample.workorder.operation;

import com.dantefung.redis.sample.workorder.entity.WorkOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @Title: AbstractSubmitOperateStrategy
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/02 22/44
 * @since JDK1.8
 */
@Slf4j
public abstract class AbstractSubmitOperateStrategy extends AbstractOperateStrategy {

	WorkOrder buildWorkOrder(OperateContext context) {
		WorkOrder workOrder = new WorkOrder();
		workOrder.setWorkCode(StringUtils.defaultString(context.getOperateParam().getWorkCode(), UUID.randomUUID().toString()));
		context.setWorkOrder(workOrder);
		return workOrder;
	}

	void operationExtend(OperateContext context){
		log.info("----->parent operationExtend ...");
	}

	void setDelayedTime(OperateContext context){
		log.info("----->parent setDelayedTime ...");
	}

	@Override
	void execute(OperateContext context) {
		prepare(context);
		paramCheck(context);
		buildWorkOrder(context);
		operationExtend(context);
	}

	@Override
	public void prepare(OperateContext context){
		log.info("----->parent prepare ...");

	}

	boolean paramCheck(OperateContext context){
		log.info("----->parent paramCheck ...");
		return true;
	}
}
