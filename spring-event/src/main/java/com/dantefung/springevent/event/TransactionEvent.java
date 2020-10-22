/*
 * Copyright (C), 2015-2018
 * FileName: TransactionEvent
 * Author:   DANTE FUNG
 * Date:     2020/7/13 17:03
 * Description: 考勤模块事务事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/13 17:03   V1.0.0
 */
package com.dantefung.springevent.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Title: TransactionEvent
 * @Description: 考勤模块事务事件
 * @author DANTE FUNG
 * @date 2020/7/13 17:03
 */
public class TransactionEvent extends ApplicationEvent {

	@Getter
	private transient TransactionCapalityEvent event;
	/**
	 * Create a new ApplicationEvent.
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public TransactionEvent(Object source, TransactionCapalityEvent event) {
		super(source);
		this.event = event;
	}
}
