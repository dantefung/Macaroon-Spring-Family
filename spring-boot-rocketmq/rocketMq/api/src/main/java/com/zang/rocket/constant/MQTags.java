package com.zang.rocket.constant;

/**
 * @author Zhang Qiang
 * @date 2019/11/28 15:37
 */
public enum MQTags {
    /**
     *
     */
    TRANSACTION_TAG("transaction_tag"),
    SPRINGBOOT_TAG("springboot_tag");

    private final String tag;
    public String getTag(){
        return tag;
    }
    MQTags(String tag){
        this.tag = tag;
    }
}
