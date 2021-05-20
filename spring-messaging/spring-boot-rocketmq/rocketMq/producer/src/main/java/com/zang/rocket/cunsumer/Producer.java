package com.zang.rocket.cunsumer;

import com.zang.rocket.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author Zhang Qiang
 * @date 2019/10/31 9:51
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception {
        defaultMqProducer(PropertiesConfig.producerGroup, PropertiesConfig.namervAddr, PropertiesConfig.topic);

    }

    public static void defaultMqProducer(String producerGroup, String namervAddr, String topic){
        DefaultMQProducer defaultMqProducer = new DefaultMQProducer(producerGroup);
        defaultMqProducer.setNamesrvAddr(namervAddr);
        try {
            defaultMqProducer.start();
            String message = "send message by mq to ";
            for (int i = 0; i < 3; i++) {
                String sendMsg = new String((" 发送aaa ：" + message + i).getBytes(), "UTF-8" );
                Message msg = new Message(topic, "push", "key_" + i, sendMsg.getBytes());
                SendResult result = defaultMqProducer.send(msg);
                log.info("发送 ： {} , result : {} ", msg, result);
            }
        } catch (Exception e) {
            log.info(" message：{} ", e.getMessage() );
        } finally {
            defaultMqProducer.shutdown();
        }
    }

}
