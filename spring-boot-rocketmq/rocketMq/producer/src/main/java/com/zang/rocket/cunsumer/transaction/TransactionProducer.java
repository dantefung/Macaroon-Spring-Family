package com.zang.rocket.cunsumer.transaction;

import com.zang.rocket.constant.MQTransactionConstant;
import com.zang.rocket.cunsumer.transaction.listener.TransactionListenerImpl;
import com.zang.rocket.utils.RandomUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.*;

/**
 * @author Zhang Qiang
 * @date 2019/11/29 15:42
 */
public class TransactionProducer {

    private static TransactionProducer producer = new TransactionProducer();

    public static void main(String[] args) throws Exception{
        String msg = "【消息】：transaction_thread send msg 【消息】";
        producer.producer(msg, 10);

    }

    public void producer(String msg, Integer con) throws MQClientException, InterruptedException {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer(MQTransactionConstant.GROUP_NAME);
        producer.setNamesrvAddr(MQTransactionConstant.NAME_SRV);
        producer.setExecutorService(getExecutorService());
        producer.setTransactionListener(transactionListener);
        producer.start();
        for (int i = 0; i < con; i++) {
            String d = msg + new String(("==== "+i).getBytes());
            Message message = new Message(MQTransactionConstant.TOPIC, MQTransactionConstant.TAG, RandomUtil.generateShortUuid(), d.getBytes());
            System.out.println("开始发送 : " + d);
            SendResult sendResult = producer.sendMessageInTransaction(message, null);
            System.out.printf("%s%n", sendResult);
            Thread.sleep(500);
        }
        TimeUnit.MILLISECONDS.sleep(1000);
        producer.shutdown();
    }

    public ExecutorService getExecutorService(){
        return new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread();
                        thread.setName("transaction_thread");
                        return thread;
                    }
                });
    }
}
