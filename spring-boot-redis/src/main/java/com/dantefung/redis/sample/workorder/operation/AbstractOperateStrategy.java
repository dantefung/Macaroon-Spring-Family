/*
 * Copyright (C), 2015-2020
 * FileName: AbstractOperateStrategy
 * Author:   DANTE FUNG
 * Date:     2020/12/2 10:32 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/2 10:32 下午   V1.0.0
 */
package com.dantefung.redis.sample.workorder.operation;

import com.dantefung.redis.sample.workorder.entity.WorkOrder;

/**
 * @Title: AbstractOperateStrategy
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/02 22/32
 * @since JDK1.8
 */
public abstract class AbstractOperateStrategy {

	abstract void execute(OperateContext context);

	abstract void prepare(OperateContext context);

	abstract WorkOrder buildWorkOrder(OperateContext context);

	abstract void operationExtend(OperateContext context);

	abstract void setDelayedTime(OperateContext context);
}
