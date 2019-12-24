package com.dantefung.rocketmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author: DANTE FUNG
 * @date: 2019-12-24
 */
@Slf4j
public abstract class AbstractMessageResolver implements MessageResolver {

    @Autowired
	private ResolverRegister register;

    @PostConstruct
    public void init() {
    	log.info("开始装载消息解析器[{}]...", this.getClass().getSimpleName());
        register.register(getTopic(), getTag(), this);
		log.info("{} registered the topic:{} and the tag: {} into {}", this.getClass().getSimpleName(), getTopic(), getTag(), register.getClass().getSimpleName());
	}

    protected abstract String getTopic();

    /**格式:TagA || TagC || TagD */
    protected abstract String getTag();
}
