package com.zang.rocket.constant;

/**
 * @author Zhang Qiang
 * @date 2019/11/28 17:24
 */
public enum  MQTopic {
    /**
     *
     */
    TOPIC_USER("sync-user-info"),
    SPRINGBOOT_TOPIC("springboot_topic"),
    DEFAULT_TOPIC("default_topic");

    private final String topic;
    public String getTopic(){
        return topic;
    }
    MQTopic(String topic){
        this.topic = topic;
    }
}
