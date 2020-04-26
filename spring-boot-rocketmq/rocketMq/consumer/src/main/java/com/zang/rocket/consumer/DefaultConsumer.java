package com.zang.rocket.consumer;

import com.zang.rocket.constant.MQGroup;
import com.zang.rocket.constant.MQTopic;
import com.zang.rocket.consumer.listener.MessageListenerConcurrentlyImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 默认消息消费者
 * DefaultMQPushConsumer
 * 需要设置
 * namesrvAddr:服务地址
 * consumerGroup:消费者群组
 * topic:消费主题
 *
 * 模式：
 * Clustering（集群）模式下每个ConsumerGroup里的每一个consumer只消费订阅主题消息的一部分，同一个consumerGroup内所有的consumer消费的内容合起来才是所订阅的topic内容的整体
 * Broadcasting（广播）模式下同一个ConsumerGroup里的每一个consumer都可以消费到订阅topic的全部消息
 *
 *
 * @author Zhang Qiang
 * @date 2019/11/28 17:08
 */
@Slf4j
public class DefaultConsumer {

    public static void main(String[] args) throws MQClientException {
        new DefaultConsumer().consumerDefault();
    }

    public void consumerDefault() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MQGroup.GLOBE_GROUP.getGroup());
        consumer.setNamesrvAddr("127.0.0.1:9876");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(MQTopic.TOPIC_USER.getTopic(), "*");
        consumer.registerMessageListener(new MessageListenerConcurrentlyImpl());
        log.info("==========启动DefaultConsumer==========");
        consumer.start();
    }

}
