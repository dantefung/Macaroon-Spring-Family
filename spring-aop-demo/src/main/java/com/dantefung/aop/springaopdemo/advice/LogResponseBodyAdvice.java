package com.dantefung.aop.springaopdemo.advice;

import com.dantefung.aop.springaopdemo.annotation.AppControllerMapping;
import com.dantefung.aop.springaopdemo.entity.AppsysEventLog;
import com.dantefung.aop.springaopdemo.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.websocket.SendResult;


@ControllerAdvice(annotations = AppControllerMapping.class)
@Slf4j
@ConditionalOnProperty(
        value = "logctr.eventlog.plugin.tokenaspect.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class LogResponseBodyAdvice implements ResponseBodyAdvice<Object>/*, SendCallback */{
    //@Autowired
    //private RocketMQTemplate rocketMQTemplate;
    //@Value("${rocketmq.producer.group}")
    private String producerGroup;
    //@Value("${rocketmq.topic.logctr.appSysEventLogTopic}")
    private String userOrgCtrUserTopic;
    @Autowired
    private ApplicationContext context;
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

    	log.info("\r\n\r\n<====================MVC回写截获处理开始...====================>\r\n");
        AppsysEventLog eventLog = LogContext.get();// 从当前线程里取得绑定的变量.
        if(eventLog==null){
        	log.warn("----->eventLog为空!");
            return body;
        }

        String destination  = userOrgCtrUserTopic + ":" + "AppsysEventLogTag";
        String responseBody = null;
        if(body==null) {
            responseBody="";
        }else if(body.getClass()==String.class){
            responseBody = (String) body;
        }else {
            ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
            try {
                responseBody = objectMapper.writeValueAsString(body);
            } catch (JsonProcessingException e) {
                log.error("", e);
            }
        }
        log.info("-------> 回写内容:{}", responseBody);
        eventLog.setEventDataOutput(responseBody);
		log.info(JsonUtil.obj2Json(eventLog));
		log.info("\r\n\r\n<====================MVC回写截获处理结束...====================>\r\n");
		//        Message msg =  MessageBuilder.withPayload(eventLog).setHeader("TRANSACTION_ID", "RMQ_TRAN_ID_" + eventLog.getId()).setHeader("KEYS", "RMQ_KEYS_" + eventLog.getId()).build();
        //异步发送消息
//        rocketMQTemplate.asyncSend(destination,msg,this);

        return body;

    }

   /* @Override
    public void onSuccess(SendResult sendResult) {
        log.info("-------| send message to broker[{}]-topic[{}]-queueid[{}]  : msg body = {} , sendResult={} , messageId={}, transactionId={}, sendResultObject={}",
                sendResult.getMessageQueue().getBrokerName(), sendResult.getMessageQueue().getTopic(), sendResult.getMessageQueue().getQueueId(), "", sendResult.getSendStatus(), sendResult.getMsgId(), sendResult.getTransactionId(), sendResult);

    }

    @Override
    public void onException(Throwable e) {
        log.error("消息发送异常", e);
    }*/
}
