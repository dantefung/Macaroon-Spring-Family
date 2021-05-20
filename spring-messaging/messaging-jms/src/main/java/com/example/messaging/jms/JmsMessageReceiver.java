package com.example.messaging.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 定义消息接收者
 *
 * @author ben
 */
@Component
public class JmsMessageReceiver {

    /**
     * 接收消息（通道：messageBox，消息工厂：jmsMessageFactory）
     *
     * @param message 消息主体
     */
    @JmsListener(destination = JmsMessageConfig.MESSAGE_BOX, containerFactory = JmsMessageConfig.MESSAGE_FACTORY)
    public void receiveMessage(JmsMessage message) {
        System.out.println("Received <" + message + ">");
    }
}
