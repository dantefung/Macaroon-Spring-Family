package com.dantefung.rocketmq.annotation.samples.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SyncInTransactionProducer {
 
    @Resource
    private RocketMQTemplate rocketMQTemplate;
 
    public TransactionSendResult sendSyncMessage(String msg, String topic, String tag){
        log.info("【发送消息】：{}", msg);
        Message message = MessageBuilder.withPayload(msg).build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("TEST_MESSAGE_IN_TRANSACTION_GROUP", topic, message, tag);
        log.info("【发送状态】：{}", result.getLocalTransactionState());
        return result;
    }
 
}