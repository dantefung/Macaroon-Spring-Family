package com.example.message.rabbitmq;

import lombok.Data;

/**
 * 消息接收器
 *
 * @author ben
 */
@Data
public class RabbitMqMessageReceiver {

    /**
     * 消息接收方法
     */
    public static final String RECEIVE_MESSAGE_METHOD = "receiveMessage";

    /**
     * 接收消息
     *
     * @param message 消息内容
     */
    public void receiveMessage(RabbitMqMessage message) {
        System.out.printf("Received <%s> %n", message);
    }


}
