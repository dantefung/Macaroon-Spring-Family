package com.dantefung.rocketmq.mq.resovler;

import com.dantefung.rocketmq.mq.AbstractMessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestMessageResolver extends AbstractMessageResolver {

	private String topic="DEMO_TEST_TOPIC";
	private String tag="DEMO";

	@Override
	protected String getTopic() {
		return this.topic;
	}

	@Override
	protected String getTag() {
		return this.tag;
	}

	@Override
	public void handle(String content) {
		log.info("receive message:[{}] from MQ  topic:[{}]  tag:[{}] ...", content, getTopic(), getTag());
	}
}
