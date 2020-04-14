package com.dantefung.rocketmq.annotation.samples.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RocketMQMessageListener(topic = "TEST_MESSAGE_IN_TRANSACTION_TOPIC", consumerGroup = "TEST_MESSAGE_IN_TRANSACTION_GROUP")
public class SyncInTransactionConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
 
    @Autowired
    ConsumerService service;
 
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently)(exts, context) ->{
            try {
                log.info("【获取消息】");
                if (!CollectionUtils.isEmpty(exts)) {
                    exts.forEach(ext->{
                        Integer con = ext.getReconsumeTimes();
                        service.readMessage(new String(ext.getBody()));
                        log.info("【消费消息】 次数：{}, ext ：{}", con, ext);
                    });
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e){
                e.printStackTrace();
                log.error("【消费消息失败】，message：{}", e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
    }
 
    @Override
    public void onMessage(MessageExt messageExt) {
        String msg = null;
        try {
            msg = new String(messageExt.getBody(),"utf-8");
            log.info("MsgConsumer >>> {}, msgId:{}", msg, messageExt.getMsgId());
            service.readMessage(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("【消费异常】：{}", e.getMessage());
        }
    }
 
 
}