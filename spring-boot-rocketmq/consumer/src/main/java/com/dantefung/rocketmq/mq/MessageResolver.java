package com.dantefung.rocketmq.mq;

/**
 * @author: DANTE FUNG
 * @date: 2019-12-24
 */
public interface MessageResolver {
    void handle(String content);
}
