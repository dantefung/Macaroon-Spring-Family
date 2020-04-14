package com.dantefung.rocketmq.annotation.samples.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应用
 * 1，交易状态
 *
 * 事务消息有三种状态：
 * （1）TransactionStatus.CommitTransaction：提交事务，表示允许使用者使用此消息。
 * （2）TransactionStatus.RollbackTransaction：回滚事务，表示该消息将被删除且不允许使用。
 * （3）TransactionStatus.Unknown：中间状态，表示需要MQ进行回溯以确定状态。
 */
@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "TEST_MESSAGE_IN_TRANSACTION_GROUP")
public class SyncInTransactionProducerListener implements RocketMQLocalTransactionListener {

	private AtomicInteger trnner = new AtomicInteger(0);
	private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();

	@Autowired
	private LocalService localService;

	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
		try {
			localService.executeLocalService(message.getPayload().toString());
			log.info("【本地业务执行完毕】 msg:{}, Object:{}", message, o);
			localTrans.put(message.getHeaders().getId() + "", message.getPayload());
			return RocketMQLocalTransactionState.COMMIT;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("【执行本地业务异常】 exception message:{}", e.getMessage());
			return RocketMQLocalTransactionState.ROLLBACK;
		}
	}

	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
		log.info("【执行检查任务】");
		return RocketMQLocalTransactionState.UNKNOWN;
	}
}