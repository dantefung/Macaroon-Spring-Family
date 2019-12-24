package com.dantefung.rocketmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 普通消息也叫做无序消息，简单来说就是没有顺序的消息，
 * producer 只管发送消息，consumer 只管接收消息，
 * 至于消息和消息之间的顺序并没有保证，
 * 可能先发送的消息先消费，也可能先发送的消息后消费。
 *
 * 因为不需要保证消息的顺序，所以消息可以大规模并发地发送和消费，吞吐量很高，适合大部分场景。
 *
 *
 * @author: DANTE FUNG
 * @date: 2019-12-24
 */
@Slf4j
@Component
public class RocketMQRegister implements ResolverRegister, MessageListenerConcurrently,
		ApplicationListener<ApplicationReadyEvent> {

    @Value("${rocketmq.consumer.cs-group.test-contract.schedulingPlan}")
    private String group;

    @Value("${rocketmq.name-server}")
    private String namesrv;

    @Value("${rocketmq.consumer.access-key}")
    private String accessKey;

	@Value("${rocketmq.consumer.secret-key}")
    private String secretKey;

    /**topic-tag**/
    private Map<String, String> subscription = new ConcurrentHashMap<>();

    /**topic-tag-resolver**/
    private Map<String, Map<String, MessageResolver>> resolverMap = new ConcurrentHashMap<>();

    private DefaultMQPushConsumer mqPushConsumer;

    @Override
    public void register(String topic, String tag, MessageResolver resolver) {
        subscription.put(topic, tag);
        Map<String, MessageResolver> hm = resolverMap.get(topic);
        if (hm == null) {
            synchronized (this) {
                hm = resolverMap.get(topic);
                if (hm == null) {
                    hm = new ConcurrentHashMap<>();
                    resolverMap.put(topic, hm);
                }
            }
        }

        hm.put(tag, resolver);
        log.info("register topic:{}  tag:{} resolver:{} ", topic, tag, resolver);
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt messageExt : list) {
                String topic = messageExt.getTopic();
                String tag = messageExt.getTags();
                if (resolverMap.containsKey(topic)) {
                    Map<String, MessageResolver> hm = resolverMap.get(topic);
                    if (hm.containsKey(tag)) {
                        MessageResolver handler = hm.get(tag);
                        handler.handle(new String(messageExt.getBody(), Charsets.UTF_8));
                    } else {
						log.warn("consumeMessage not found resolver for tag:{} topic:{}  {}", tag, topic, resolverMap);
                    }
                } else {
					log.warn("consumeMessage not found resolver for tag:{} topic:{}  {}", tag, topic, resolverMap);
                }
            }
        } catch (Throwable t) {
			//RECONSUME_LATER 消费失败，需要稍后重新消费
            log.error("consumeMessage error!", t);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }


        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("开始启动MQ消息消费端...");
		try {
			initListener();
		} catch (Exception e) {
			log.error("onApplicationEvent ", e);
			throw new RuntimeException(e);
		}
		log.info("启动MQ消息消费端完成...");

	}

    private void initListener() throws MQClientException {
		log.info("initListener ...");
        mqPushConsumer = getConsumer();
		subscription.entrySet().forEach(entry->{
			try {
				mqPushConsumer.subscribe(entry.getKey(), entry.getValue());
			} catch (MQClientException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		});
        mqPushConsumer.registerMessageListener(this);
        mqPushConsumer.start();
		log.info("initListener ok！");
    }

    private DefaultMQPushConsumer getConsumer() {
        final DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(group, getAclRPCHook(), new AllocateMessageQueueAveragely());
        defaultMQPushConsumer.setNamesrvAddr(namesrv);
		// 消费策略: CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1024);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return defaultMQPushConsumer;
    }

	private RPCHook getAclRPCHook() {
		return new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
	}

    @PreDestroy
    public void destroy() {
        if (mqPushConsumer != null) {
            mqPushConsumer.shutdown();
        }
		log.info("destroy");
    }

}
