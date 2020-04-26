package com.zang.rocket.producer;

import com.zang.rocket.service.LocalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zhang Qiang
 * @date 2019/12/4 16:07
 */
@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "spring_boot_producer_group")
public class SyncProducerListener implements RocketMQLocalTransactionListener {
    private AtomicInteger trnner = new AtomicInteger(0);
    private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();
    @Autowired
    private LocalService localService;
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            localService.executeLocalService(message.getPayload().toString());
            log.info("【本地业务执行完毕】 msg:{}, Object:{}", message, o);
            localTrans.put(message.getHeaders().getId()+"", message.getPayload());
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【执行本地业务异常】 exception message:{}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("【执行检查任务】");
        return RocketMQLocalTransactionState.UNKNOWN;
    }
}
