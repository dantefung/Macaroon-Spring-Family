package com.dantefung.rocketmq.annotation.samples.transaction;

/**
 * @Title: ConsumerService
 * @Description:
 * @author DANTE FUNG
 * @date 2020/4/14 19:31
 */
public interface ConsumerService {

	void readMessage(String message);
}
