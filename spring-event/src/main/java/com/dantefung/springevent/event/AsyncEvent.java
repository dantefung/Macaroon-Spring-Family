/*
 * Copyright (C), 2015-2018
 * FileName: AsyncEvent
 * Author:   DANTE FUNG
 * Date:     2020/7/14 18:07
 * Description: 异步事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/14 18:07   V1.0.0
 */
package com.dantefung.springevent.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Title: AsyncEvent
 * @Description: 异步事件
 * @author DANTE FUNG
 * @date 2020/7/14 18:07
 */
public class AsyncEvent extends ApplicationEvent {

	@Getter
	private transient AsyncCapalityEvent event;

	/**
	 * Create a new ApplicationEvent.
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public AsyncEvent(Object source, AsyncCapalityEvent event) {
		super(source);
		this.event = event;
	}
}
