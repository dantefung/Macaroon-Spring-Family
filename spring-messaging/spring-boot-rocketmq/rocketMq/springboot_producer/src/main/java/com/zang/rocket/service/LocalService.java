package com.zang.rocket.service;

import com.zang.rocket.constant.MQTags;
import com.zang.rocket.constant.MQTopic;
import com.zang.rocket.producer.SyncProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zhang Qiang
 * @date 2019/12/4 16:10
 */
@Slf4j
@Service
public class LocalService {

    @Autowired
    private SyncProducer syncProducer;

    public String executeLocalService(){
        String msg = "执行本地业务";
        return this.executeLocalService(msg);
    }

    public String executeLocalService(String msg){
        log.info("【执行业务，读取发送消息】：{}", msg);
        return msg;
    }

    public String sentSyncMessage(String s){
        TransactionSendResult result = syncProducer.sendSyncMessage(s, MQTopic.SPRINGBOOT_TOPIC.getTopic(), MQTags.SPRINGBOOT_TAG.getTag());
        return result.getLocalTransactionState().toString();
    }


}
