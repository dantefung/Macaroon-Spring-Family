/*
 * Copyright (C), 2015-2018
 * FileName: SyncInTransctionProduceController
 * Author:   DANTE FUNG
 * Date:     2020/4/14 19:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/14 19:37   V1.0.0
 */
package com.dantefung.rocketmq.annotation.samples.transaction;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: SyncInTransctionProduceController
 * @Description:
 * @author DANTE FUNG
 * @date 2020/4/14 19:37
 */
@RestController
@RequestMapping
public class SyncInTransctionProduceController {

	@Autowired
	private SyncInTransactionProducer syncInTransactionProducer;

	@GetMapping("/produceMessageInTransaction")
	@ResponseBody
	public TransactionSendResult produceMessageInTransaction() {
		String topic = "TEST_MESSAGE_IN_TRANSACTION_TOPIC";
		String tag = "TEST_MESSAGE_IN_TRANSACTION_TAG";
		String payload = "{\"group\": \"TEST_MESSAGE_IN_TRANSACTION_GROUP\",\"topic\": \"TEST_MESSAGE_IN_TRANSACTION_TOPIC\", \"tag\": \"TEST_MESSAGE_IN_TRANSACTION_TAG\"}";
		return syncInTransactionProducer.sendSyncMessage(payload, topic, tag);
	}

	/*
		{
		"sendStatus": "SEND_OK",
		"msgId": "AC147D5160E418B4AAC247318EA30000",
		"messageQueue": {
		"topic": "TEST_MESSAGE_IN_TRANSACTION_TOPIC",
		"brokerName": "dev-broker",
		"queueId": 0
		},
		"queueOffset": 320892,
		"transactionId": null,
		"offsetMsgId": null,
		"regionId": null,
		"traceOn": true,
		"localTransactionState": "COMMIT_MESSAGE"
		}
	 */
}
