package com.zang.rocket.constant;

/**
 * @author Zhang Qiang
 * @date 2019/11/28 15:32
 */
public enum MQConstant {

    /**
     *
     */
    TOPIC_DQ("topic_data_queue_message"),
    TOPIC_USER("sync-user-info"),
    TOPIC_MESSAGE("topic_message");

    private final String constant;
    public String getConstant(){
        return constant;
    }
    MQConstant(String constant){
        this.constant = constant;
    }
}
