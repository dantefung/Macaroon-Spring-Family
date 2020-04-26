package com.zang.rocket.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Zhang Qiang
 * @date 2019/11/19 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDateMessage<T> implements Serializable {
    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 编码
     */
    private Integer code = 100000;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 消息内容
     */
    private T date;

}
