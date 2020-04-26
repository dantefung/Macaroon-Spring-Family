package com.zang.rocket.consumer.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Zhang Qiang
 * @date 2019/11/29 17:41
 */
public class MessageListenerOrderlyImpl implements MessageListenerOrderly {

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        try {
            for (MessageExt msg : list) {
                System.out.println("msg:" + new String(msg.getBody()));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        } catch (Exception e) {
            System.out.println("【消费异常】 msg：" + e.getMessage());
            e.printStackTrace();
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
    }
}
