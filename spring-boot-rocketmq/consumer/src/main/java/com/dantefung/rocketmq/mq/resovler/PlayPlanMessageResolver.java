package com.dantefung.rocketmq.mq.resovler;

import com.dantefung.rocketmq.mq.AbstractMessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PlayPlanMessageResolver extends AbstractMessageResolver {

	@Value("${rocketmq.topic.test-mq.schedulingPlanTopic}")
	private String topic;
	/**约定tag和group一样的值**/
	@Value("${rocketmq.consumer.cs-group.test-contract.schedulingPlan}")
	private String tag;

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
