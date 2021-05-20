package com.dantefung.rocketmq.mq;

/**
 * @author DANTE FUNG
 * @date 2019年12月24日 10:27:11
 * @description 解析器注册中心
 */
public interface ResolverRegister {
    void register(String topic, String tag, MessageResolver handler);
}
