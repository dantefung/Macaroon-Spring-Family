package com.dantefung.springevent.notify;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpNotifyHandler implements RetryCallback<Void, Exception> {

	private HttpClient httpClient;

	private String url;

	public HttpNotifyHandler(NotifyUri uri) {
		this.httpClient = HttpClientBuilder.create().build();
		this.url = uri.buildGetUrl();
	}

	@Override
	public Void doWithRetry(RetryContext context) throws Exception {
		if(context.getLastThrowable()!=null) {
			log.error("",context.getLastThrowable());
		}
		log.info("第{}次 发起请求:{}", context.getRetryCount() + 1, url);
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				log.info("通知成功");
			} else {
				log.warn("通知失败,重试");
				throw new HttpClientErrorException(HttpStatus.resolve(response.getStatusLine().getStatusCode()));
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

}
