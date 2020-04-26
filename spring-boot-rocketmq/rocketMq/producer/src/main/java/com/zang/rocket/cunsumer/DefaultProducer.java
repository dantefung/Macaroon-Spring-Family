package com.zang.rocket.cunsumer;

import com.zang.rocket.constant.MQGroup;
import com.zang.rocket.constant.MQTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 默认消息
 * @author Zhang Qiang
 * @date 2019/11/28 17:31
 */
@Slf4j
public class DefaultProducer {
    public static void main(String[] args) throws Exception {
        String data = "你好，你好，好啊有，爱慕饭，三克油，俺的有？爱慕饭图，三克油！";
        new DefaultProducer().sendDefault(data);
    }

    /**
     * 默认消息
     */
    public void sendDefault(String data) throws Exception {
        log.info("======发送mq======");
        DefaultMQProducer defaultMqProducer = new DefaultMQProducer(MQGroup.GLOBE_GROUP.getGroup());
        defaultMqProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMqProducer.start();

        for (int i = 0; i < 10; i++) {
            data += "=====" +i ;
            Message message = new Message(MQTopic.TOPIC_USER.getTopic(), "push", "key", data.getBytes());
            SendResult result = defaultMqProducer.send(message);
            log.info("发送 ： {} , result : {} ", data, result);
        }

        defaultMqProducer.shutdown();

    }

}
