/*
 * Copyright (C), 2015-2018
 * FileName: ApplyConditionObject
 * Author:   DANTE FUNG
 * Date:     2020/7/9 13:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/9 13:56   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.expression;

import lombok.Builder;
import lombok.Data;

/**
 * @Title: ApplyConditionObject
 * @Description:
 * @author DANTE FUNG
 * @date 2020/7/9 13:56
 */
public class ApplyConditionObject implements ConditionObject<ApplyConditionObject.ApplyCondition> {

	private ApplyCondition condition;

	public ApplyConditionObject(ApplyCondition condition) {
		this.condition = condition;
	}

	@Override
	public ApplyCondition getObject() {
		return condition;
	}

	@Override
	public Class<?> getObjectType() {
		return ApplyCondition.class;
	}

	@Builder
	@Data
	public static class ApplyCondition {
		//员工级别
		private int grade;
		//请假类型
		//@see NodeTypeEnum
		private String type;

		//请假天数
		private int days;
	}
}
