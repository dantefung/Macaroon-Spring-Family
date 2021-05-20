package com.dantefung.rocketmq.annotation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Slf4j
@Service
@RocketMQMessageListener(topic = "${rocketmq.topic.test-mq.schedulingPlanTopic}", consumerGroup = "${rocketmq.consumer.cs-group.test-contract.schedulingPlan}",
        messageModel = MessageModel.CLUSTERING, selectorExpression = "TEST_CONTRACT_TOPIC_CS_GROUP") //topic为提前在rocketMq控制台中提前定义好
public class ListenerDemo implements RocketMQListener<MessageExt> {
    /**
     * Consumer 实现注意：
     *      RocketMQ不保证消息不重复发送，因此需要在消息的消费端进行消息的消费判重，即如果消费过，则不再重复执行对应的业务逻辑。
     *
     *
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {
        try{
            log.info("ListenerDemo 开始接收");
            log.info("MQ Message Header - TransactionId=[{}], Keys=[{}]", message.getProperty(RocketMQHeaders.TRANSACTION_ID), message.getProperty(RocketMQHeaders.KEYS));
            String msg = RemotingSerializable
					.fromJson(StringUtils.toEncodedString(message.getBody(), Charset.forName("UTF-8")), String.class);
            log.info("ListenerDemo 接收到消息，消息为：{}",msg);
 
            log.info("--------ListenerDemo Mq结束");
        }catch (Exception e){
            log.error("onMessage Exception>> ", e);
        }
    }
}