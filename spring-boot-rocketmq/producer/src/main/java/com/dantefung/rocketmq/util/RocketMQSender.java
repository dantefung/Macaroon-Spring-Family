package com.dantefung.rocketmq.util;

import com.dantefung.rocketmq.exception.MsgBusException;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;

public class RocketMQSender {
    private static final String RMQ_TRAN_ID_PREFIX = "RMQ_TRAN_ID_";
    private static final String RMQ_KEYS_PREFIX = "RMQ_KEYS_";
    private RocketMQTemplate rocketMQTemplate;
    private String txProducerGroup;
    private String topic;
    private String tag;

    public RocketMQSender() {
    }

    public RocketMQSender(RocketMQTemplate rocketMQTemplate, String topic) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.topic = topic;
    }

    public RocketMQTemplate getRocketMQTemplate() {
        return this.rocketMQTemplate;
    }

    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public String getTxProducerGroup() {
        return this.txProducerGroup;
    }

    public void setTxProducerGroup(String txProducerGroup) {
        this.txProducerGroup = txProducerGroup;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public <T> SendResult sendMessageInTransaction(String tranId, T msgPayload) {
        Message msg = this.genDefaultMqMsgObj(tranId, msgPayload);
        SendResult sendResult = this.rocketMQTemplate.sendMessageInTransaction(this.txProducerGroup, this.getDestinationString(), msg, (Object)null);
        this.checkRmqSendResult(sendResult);
        return sendResult;
    }

    public <T> SendResult syncSend(String tranId, T msgPayload) {
        Message msg = this.genDefaultMqMsgObj(tranId, msgPayload);
        SendResult sendResult = this.rocketMQTemplate.syncSend(this.getDestinationString(), msg);
        this.checkRmqSendResult(sendResult);
        return sendResult;
    }

    public <T> Message genDefaultMqMsgObj(String tranId, T msgPayload) {
        return MessageBuilder.withPayload(msgPayload).setHeader("TRANSACTION_ID", "RMQ_TRAN_ID_" + tranId).setHeader("KEYS", "RMQ_KEYS_" + tranId).build();
    }

    public String getDestinationString() {
        return StringUtils.isNotEmpty(this.tag) ? this.topic + ":" + this.tag : this.topic;
    }

    public void checkRmqSendResult(SendResult sendResult) {
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            throw MsgBusException.MQ_SEND_MSG_ERROR.fmt(new Object[]{sendResult.getSendStatus()});
        }
    }

    public static class Builder {
        private RocketMQTemplate rocketMQTemplate;
        private String txProducerGroup;
        private String topic;
        private String tag;

        public Builder(RocketMQTemplate rocketMQTemplate, String topic) {
            this.rocketMQTemplate = rocketMQTemplate;
            this.topic = topic;
        }

        public RocketMQSender.Builder withTag(String tag) {
            this.tag = tag;
            return this;
        }

        public RocketMQSender.Builder withTxProducerGroup(String txProducerGroup) {
            this.txProducerGroup = txProducerGroup;
            return this;
        }

        public RocketMQSender build() {
            Assert.isTrue(StringUtils.isNotEmpty(this.topic), "RocketMQ topic of RocketMQSender can not be null!");
            RocketMQSender rocketMQSender = new RocketMQSender(this.rocketMQTemplate, this.topic);
            if (StringUtils.isNotEmpty(this.tag)) {
                rocketMQSender.tag = this.tag;
            }

            if (StringUtils.isNotEmpty(this.txProducerGroup)) {
                rocketMQSender.txProducerGroup = this.txProducerGroup;
            }

            return rocketMQSender;
        }
    }
}
