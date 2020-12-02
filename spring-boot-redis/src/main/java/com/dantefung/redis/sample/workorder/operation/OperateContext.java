/*
 * Copyright (C), 2015-2020
 * FileName: OperateContext
 * Author:   DANTE FUNG
 * Date:     2020/12/2 10:30 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/2 10:30 下午   V1.0.0
 */
package com.dantefung.redis.sample.workorder.operation;

import com.dantefung.redis.sample.workorder.entity.WorkOrder;
import com.dantefung.redis.utils.Result;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Title: OperateContext
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/02 22/30
 * @since JDK1.8
 */
@Builder
@Data
public class OperateContext {

	private OperateStrategyEnum operateStrategyEnum;
	private OperateParam operateParam;
	private WorkOrder workOrder;

	private Result result;

	public enum OperateStrategyEnum {
		STORE_WORK_ORDER,
		DELAYED_SCHEDULED_ORDER,
		SUSPEND_WORK_ORDER
	}

	public void operateStrategyEnum(OperateStrategyEnum operateStrategyEnum) {
		this.operateStrategyEnum = operateStrategyEnum;
	}

	public OperateStrategyEnum getOperateStrategyEnum() {
		return operateStrategyEnum;
	}

	public void buildExecuteResultWithSuccess() {
		this.result = Result.ok();
	}

	public Result<Boolean> getExecuteResult() {
		return this.result;
	}

	@Builder
	@Data
	public static class OperateParam {
		private String workCode;
		private Date delayedTime;
		private int delayedSeconds;
	}
}
