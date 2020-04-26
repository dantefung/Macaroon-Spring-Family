package com.zang.rocket.mq.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Zhang Qiang
 * @date 2019/11/19 10:38
 */
public class MQListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt ext : list) {
            try {
                String msg = new String(ext.getBody(), "utf-8");
                System.out.println("msg:" + msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
//        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
