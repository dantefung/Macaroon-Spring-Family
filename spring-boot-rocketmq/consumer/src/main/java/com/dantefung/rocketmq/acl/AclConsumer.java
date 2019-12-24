package com.dantefung.rocketmq.acl;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;

import java.util.List;

public class AclConsumer {

	public static void main(String[] args) throws InterruptedException, MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("DANTE_ADVERT_CONTRACT_TOPIC_CS_GROUP", getAclRPCHook(),
				new AllocateMessageQueueAveragely());
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.subscribe("ADVERT_DEVICECTR_TOPIC", "DANTE_ADVERT_CONTRACT_TOPIC_CS_GROUP");
		consumer.setNamesrvAddr("192.168.126.130:9876");
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}

	static RPCHook getAclRPCHook() {

		return new AclClientRPCHook(new SessionCredentials("123456User", "Abc123456aBc"));
	}
}