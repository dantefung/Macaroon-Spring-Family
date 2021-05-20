package com.dantefung.rocketmq.acl;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class AclProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("DANTE_please_rename_unique_group_name", getAclRPCHook());
        producer.setNamesrvAddr("192.168.126.130:9876");
        producer.start();
        for (int i = 0; i < 1; i++) {
            try {
                Message msg = new Message("DEMO_TEST_TOPIC" ,"DEMO" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        producer.shutdown();
    }

    static RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials("xxxUser","Abc12xx56aBc"));
    }
}