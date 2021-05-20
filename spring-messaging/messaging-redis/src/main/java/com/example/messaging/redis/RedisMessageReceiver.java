package com.example.messaging.redis;

/**
 * 消息接收器
 *
 * @author ben
 */
public class RedisMessageReceiver {

    /**
     * 消息接收方法
     */
    public static final String RECEIVE_MESSAGE_METHOD = "receiveMessage";

    /**
     * 接收消息
     *
     * @param message 消息内容
     * @param channel 接收消息对应通道
     */
    public void receiveMessage(String message, String channel) {
        System.out.printf("From<%s> Received <%s> %n", channel, message);
    }

}
