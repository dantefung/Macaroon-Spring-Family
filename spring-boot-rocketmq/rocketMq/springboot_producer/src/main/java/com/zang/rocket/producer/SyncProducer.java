package com.zang.rocket.producer;

import com.zang.rocket.constant.MQGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Zhang Qiang
 * @date 2019/12/4 15:55
 */
@Slf4j
@Service
public class SyncProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public TransactionSendResult sendSyncMessage(String msg, String topic, String tag){
        log.info("【发送消息】：{}", msg);
        Message message = MessageBuilder.withPayload(msg).build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(MQGroup.SPRING_BOOT_PRODUCER_GROUP.getGroup(), topic, message, tag);
        log.info("【发送状态】：{}", result.getLocalTransactionState());
        return result;
    }

}


