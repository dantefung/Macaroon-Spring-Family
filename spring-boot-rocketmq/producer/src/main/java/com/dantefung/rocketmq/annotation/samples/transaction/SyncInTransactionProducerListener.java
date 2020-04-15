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

	/**
	 * Broker 回查调用的方法
	 *
	 * RMQ是通过Half-Message机制来实现MQ事务的，因此需要实现一个Listener来让RMQ 服务器端回调（回查）
	 * ，当收不到Half-Message的确认消息（确认后才真正发送消息出去，让消费者接收到，否则消息会被标记为异常丢弃）。
	 * 实现Listener 只需要继承 AbstractRocketMQLocalTransactionListener，然后重写实现自己的checkLocalTransaction方法即可，
	 * 例如，在该方法中查询一下订单信息是否已经正常插入数据库，如果是就自动确认Half-Message，让消息发送出去。示例代码如下：
	 * @param message
	 * @return
	 */
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
		log.info("【执行检查任务】");
		return RocketMQLocalTransactionState.UNKNOWN;
		// 模拟检查之前DB事务是否正常提交，正常提交，则提交MQ事务。
		//log.info("DB Transaction has been checked successfully! >>>>> ");
		//return RocketMQLocalTransactionState.COMMIT;
	}
}