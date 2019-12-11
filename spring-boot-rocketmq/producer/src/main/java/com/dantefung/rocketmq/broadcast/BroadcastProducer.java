package com.dantefung.rocketmq.broadcast;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.UUID;

public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("Broadcast_ProducerGroupName" + UUID.randomUUID().toString());

        producer.setNamesrvAddr("192.168.126.130:8088");

        producer.start();

        for (int i = 0; i < 100; i++){
            Message msg = new Message("TopicTestBroadcast",
                "TagA",
                "OrderID188",
                "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}