package com.dantefung.springevent.notify;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadbalanceNotifyHandler implements RetryCallback<Void, Exception> {

	private RestTemplate restTemplate;

	private String url;

	public LoadbalanceNotifyHandler(NotifyUri uri, RestTemplate restTemplate) {
		this.url = uri.buildGetUrl().replace(NotifyUri.LB_PROTOCOL, NotifyUri.HTTP_PROTOCOL);
		this.restTemplate = restTemplate;
	}

	@Override
	public Void doWithRetry(RetryContext context) throws Exception {
		if(context.getLastThrowable()!=null) {
			log.error("",context.getLastThrowable());
		}
		log.info("第{}次 发起请求:{}", context.getRetryCount() + 1, url);
		ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
		if (result.getStatusCode().is2xxSuccessful()) {
			log.info("通知成功");
		} else {
			log.warn("通知失败,重试");
			throw new HttpClientErrorException(result.getStatusCode());
		}
		return null;
	}

}
