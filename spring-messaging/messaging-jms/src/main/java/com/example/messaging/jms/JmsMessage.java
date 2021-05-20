package com.example.messaging.jms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定义消息体
 *
 * @author ben
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JmsMessage {

    /**
     * 接收者
     */
    private String to;

    /**
     * 接收内容
     */
    private String content;
}
