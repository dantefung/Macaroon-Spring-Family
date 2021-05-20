package com.zang.rocket.cunsumer.transaction.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zhang Qiang
 * @date 2019/11/29 15:41
 */
@Slf4j
public class TransactionListenerImpl implements TransactionListener {

    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private AtomicInteger chectTimes = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 开始执行本地事务消息
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("发送 message: {}, Object:{}", message, o);
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(message.getTransactionId(), status);
        LocalTransactionState s = returnState(status);
        log.info("【执行本地事务】 status：{}, localTransactionState:{}", status, s);
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * 回查本地事务消息，返回相应状态
     * @param messageExt
     * @returnz
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        Integer status = localTrans.get(messageExt.getTransactionId());
        log.info("消息status:{}, messageExt:{}", status, messageExt);
        if (null != status) {
            System.out.println("【检查本地事务状态】 status: " + status + ", message: " + new String(messageExt.getBody()));
            return returnState(status);
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    private LocalTransactionState returnState(Integer status){
        switch (status) {
            case 0:
                return LocalTransactionState.UNKNOW;
            case 1:
                return LocalTransactionState.COMMIT_MESSAGE;
            case 2:
                return LocalTransactionState.ROLLBACK_MESSAGE;
            default:
                return LocalTransactionState.COMMIT_MESSAGE;
        }
    }
}