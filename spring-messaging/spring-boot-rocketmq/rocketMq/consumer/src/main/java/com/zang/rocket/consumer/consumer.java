package com.zang.rocket.consumer;

import com.zang.rocket.config.PropertiesConfig;
import com.zang.rocket.mq.listener.MQListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author Zhang Qiang
 * @date 2019/11/19 10:28
 */
@Slf4j
public class consumer {

    public static void main(String[] args) {
        consumerMsg(PropertiesConfig.producerGroup, PropertiesConfig.namervAddr, PropertiesConfig.topic);
    }

    public static void consumerMsg(String groupName, String namervAddr, String topic){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namervAddr);
        try {
            consumer.subscribe(topic,"*");
            consumer.registerMessageListener(new MQListener());
            consumer.start();
        } catch (MQClientException e) {
            log.info("MQClientException : {}", e.getErrorMessage());
        }
        System.out.printf("Consumer Started.%n");
    }




}
