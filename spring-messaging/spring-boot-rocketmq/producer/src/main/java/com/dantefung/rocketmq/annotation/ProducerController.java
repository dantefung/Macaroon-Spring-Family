package com.dantefung.rocketmq.annotation;
 
import com.alibaba.fastjson.JSONObject;
import com.dantefung.rocketmq.util.RocketMQSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("generator/producer")
public class ProducerController {
 
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
 
    @Value("${rocketmq.producer.group}")
    private String schedulingPlanTopicPdGroup;
 
    @Value("${rocketmq.topic.test-mq.schedulingPlanTopic}")
    private String springTransTopic;

	/**
	 * http://localhost:9998/generator/producer/sendMessage?message=%E8%BF%99%E6%98%AF%E7%AC%AC%E4%B8%80%E6%9D%A1MQ%E6%B5%8B%E8%AF%95!&topic=ADVERT_DEVICECTR_TOPIC&tag=ADVERT_CONTRACT_TOPIC_CS_GROUP
	 * @param message
	 * @param topic
	 * @param tag
	 * @return
	 */
    @GetMapping("/sendMessage")
	@ResponseBody
    public String sendMessage(@RequestParam("message")String message, String topic, String tag){
        log.info("ProducerController！准备就绪");
        String rocketMQId = "KEY_2";
        RocketMQSender rocketMQSender = new RocketMQSender.Builder(rocketMQTemplate, StringUtils.isNotBlank(topic)?topic:springTransTopic)
                //.withTag("ADVERT_CONTRACT_TOPIC_CS_GROUP")
                .withTag(StringUtils.isNotBlank(tag)?tag:"DANTE_TEST_CONTRACT_TOPIC_CS_GROUP")
                //.withTxProducerGroup(contractTopicPdGroup)
                .build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        String jsonStr = JSONObject.toJSONString(jsonObject);
        SendResult sendResult = rocketMQSender.syncSend(rocketMQId,jsonStr);
        log.info("------reportLedgerInsert send Transactional END --------" + sendResult.toString());
        return "ok!";
    }
}