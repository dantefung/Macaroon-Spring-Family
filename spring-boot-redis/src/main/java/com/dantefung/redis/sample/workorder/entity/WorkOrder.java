/*
 * Copyright (C), 2015-2020
 * FileName: WorkOrder
 * Author:   DANTE FUNG
 * Date:     2020/12/2 10:47 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/2 10:47 下午   V1.0.0
 */
package com.dantefung.redis.sample.workorder.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Title: WorkOrder
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/02 22/47
 * @since JDK1.8
 */
@Data
public class WorkOrder {

	private String workCode;
	private int mainStatus;
	private int subStatus;
	private boolean isFinished;
	private int suspendedCount;
	private Date delayedTime;
	private boolean isStore;
	private double casePriority;
}
