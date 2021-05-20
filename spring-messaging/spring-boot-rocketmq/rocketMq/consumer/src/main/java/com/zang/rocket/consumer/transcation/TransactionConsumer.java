package com.zang.rocket.consumer.transcation;

import com.zang.rocket.constant.MQTransactionConstant;
import com.zang.rocket.consumer.listener.MessageListenerConcurrentlyImpl;
import com.zang.rocket.consumer.listener.MessageListenerOrderlyImpl;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * @author Zhang Qiang
 * @date 2019/11/29 17:35
 */
public class TransactionConsumer {
    public static void main(String[] args) throws MQClientException {
        new TransactionConsumer().consumer();
    }

    private void consumer() throws MQClientException {
        String consumerGroup = "transaction_consumer_group";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(MQTransactionConstant.NAME_SRV);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(MQTransactionConstant.TOPIC, MQTransactionConstant.TAG);
        consumer.setMessageListener(new MessageListenerOrderlyImpl());
        consumer.registerMessageListener(new MessageListenerConcurrentlyImpl());

        consumer.start();
        System.out.println("consumer.start success!");
    }

}
