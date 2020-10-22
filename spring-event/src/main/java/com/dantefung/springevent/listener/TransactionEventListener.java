/*
 * Copyright (C), 2015-2018
 * FileName: TransactionEventListener
 * Author:   DANTE FUNG
 * Date:     2020/7/13 17:50
 * Description: 事务事件监听器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/13 17:50   V1.0.0
 */
package com.dantefung.springevent.listener;

import com.dantefung.springevent.event.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Title: TransactionEventListener
 * @Description: 事务事件监听器
 * @author DANTE FUNG
 * @date 2020/7/13 17:50
 */
@Slf4j
@Component
public class TransactionEventListener {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@TransactionalEventListener
	public void onTransactionCommit(TransactionEvent event) {
		eventPublisher.publishEvent(event.getEvent());
	}
}
