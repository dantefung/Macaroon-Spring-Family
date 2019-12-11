//package com.dantefung.rocketmq.config;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Conditional;
//import org.springframework.util.StringUtils;
//
//import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
//import com.alibaba.rocketmq.client.exception.MQClientException;
//import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
//import com.clouds.common.rocketmq.constants.RocketMQErrorEnum;
//import com.clouds.common.rocketmq.consumer.processor.MQConsumeMsgListenerProcessor;
//import com.clouds.common.rocketmq.exception.RocketMQException;
//
//
///**
// * 消费者Bean配置
// * .<br/>
// *
// * Copyright: Copyright (c) 2017  zteits
// *
// * @ClassName: MQConsumerConfiguration
// * @Description:
// * @version: v1.0.0
// * @author: zhaowg
// * @date: 2018年3月2日 下午11:48:32
// * Modification History:
// * Date             Author          Version            Description
// *---------------------------------------------------------*
// * 2018年3月2日      zhaowg           v1.0.0               创建
// */
//@SpringBootConfiguration
//public class MQConsumerConfiguration {
//    public static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerConfiguration.class);
//    @Value("${rocketmq.consumer.namesrvAddr}")
//    private String namesrvAddr;
//    @Value("${rocketmq.consumer.groupName}")
//    private String groupName;
//    @Value("${rocketmq.consumer.consumeThreadMin}")
//    private int consumeThreadMin;
//    @Value("${rocketmq.consumer.consumeThreadMax}")
//    private int consumeThreadMax;
//    @Value("${rocketmq.consumer.topics}")
//    private String topics;
//    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
//    private int consumeMessageBatchMaxSize;
//    @Autowired
//    private MQConsumeMsgListenerProcessor mqMessageListenerProcessor;
//
//    @Bean
//    public DefaultMQPushConsumer getRocketMQConsumer() throws RocketMQException {
//        if (StringUtils.isEmpty(groupName)){
//            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"groupName is null !!!",false);
//        }
//        if (StringUtils.isEmpty(namesrvAddr)){
//            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"namesrvAddr is null !!!",false);
//        }
//        if(StringUtils.isEmpty(topics)){
//            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"topics is null !!!",false);
//        }
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
//        consumer.setNamesrvAddr(namesrvAddr);
//        consumer.setConsumeThreadMin(consumeThreadMin);
//        consumer.setConsumeThreadMax(consumeThreadMax);
//        consumer.registerMessageListener(mqMessageListenerProcessor);
//        /**
//         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
//         * 如果非第一次启动，那么按照上次消费的位置继续消费
//         */
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        /**
//         * 设置消费模型，集群还是广播，默认为集群
//         */
//        //consumer.setMessageModel(MessageModel.CLUSTERING);
//        /**
//         * 设置一次消费消息的条数，默认为1条
//         */
//        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
//        try {
//            /**
//             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
//             */
//            String[] topicTagsArr = topics.split(";");
//            for (String topicTags : topicTagsArr) {
//                String[] topicTag = topicTags.split("~");
//                consumer.subscribe(topicTag[0],topicTag[1]);
//            }
//            consumer.start();
//            LOGGER.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr);
//        }catch (MQClientException e){
//            LOGGER.error("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr,e);
//            throw new RocketMQException(e);
//        }
//        return consumer;
//    }
//}