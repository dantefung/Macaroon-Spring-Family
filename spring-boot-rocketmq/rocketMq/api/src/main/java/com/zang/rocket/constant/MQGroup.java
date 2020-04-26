package com.zang.rocket.constant;

/**
 * @author Zhang Qiang
 * @date 2019/11/28 17:18
 */
public enum MQGroup {
    /**
     *
     */
    GLOBE_GROUP("boot_producer_group"),
    SPRING_BOOT_PRODUCER_GROUP("spring_boot_producer_group"),
    SPRING_BOOT_CONSUMER_GROUP("spring_boot_consumer_group"),
    CONSUMER_GROUP("consumer_group"),
    PRODUCER_GROUP("producer_group");

    private final String group;
    public String getGroup(){
        return group;
    }
    MQGroup(String constant){
        this.group = constant;
    }
}
