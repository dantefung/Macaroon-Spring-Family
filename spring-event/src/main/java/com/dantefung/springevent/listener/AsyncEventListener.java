/*
 * Copyright (C), 2015-2018
 * FileName: AsyncEventListener
 * Author:   DANTE FUNG
 * Date:     2020/7/14 18:04
 * Description: 异步事件监听器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/14 18:04   V1.0.0
 */
package com.dantefung.springevent.listener;

import com.dantefung.springevent.event.AsyncEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Title: AsyncEventListener
 * @Description: 异步事件监听器
 * @author DANTE FUNG
 * @date 2020/7/14 18:04
 */
@Slf4j
@Component
public class AsyncEventListener {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Async
	@EventListener
	public void onEvent(AsyncEvent event) {
		log.info("----> enter {}.onEvent method ...", AsyncEventListener.class.getSimpleName());
		eventPublisher.publishEvent(event.getEvent());
	}
}
