package com.dantefung.springevent.listener;

import com.dantefung.springevent.event.NotifyEvent;
import com.dantefung.springevent.notify.HttpNotifyHandler;
import com.dantefung.springevent.notify.JvmNotifyHandler;
import com.dantefung.springevent.notify.LoadbalanceNotifyHandler;
import com.dantefung.springevent.notify.NotifyUri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NotifyEventListener {
	@Autowired
	private RestTemplate restTemplate;
	private static final int RETRY_COUNT = 3;// 总的重试次数
	@Autowired
	private ApplicationContext context;
	private RetryCallback<Void, Exception> newHandler(NotifyUri uri) {
		switch (uri.getSchema()) {
		case LOAD_BALANCE:
			return new LoadbalanceNotifyHandler(uri, restTemplate);
		case HTTP:
			return new HttpNotifyHandler(uri);
		case JVM:
			return new JvmNotifyHandler(context, uri);
		case MQ:
		default:
			throw new RuntimeException("该协议未实现");
		}
	}

	@Async
	@EventListener
	public void onEvent(NotifyEvent event) {
		NotifyUri notifyUri =event.getUri();

		// 通知下游
		RetryTemplate retryTemplate = new RetryTemplate();
		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		policy.setMaxAttempts(RETRY_COUNT);
		retryTemplate.setRetryPolicy(policy);
		// 使用策略模式
		RetryCallback<Void, Exception> callback = newHandler(notifyUri);
		final String URL = notifyUri.getUrl();
		try {
			retryTemplate.execute(callback, new RecoveryCallback<Void>() {
				@Override
				public Void recover(RetryContext context) throws Exception {
					if(context.getLastThrowable()!=null) {
						log.error("",context.getLastThrowable());
					}
					// 重试次数耗尽，最后没有成功
					log.info("重试了{}次，通知依然没有成功{}", RETRY_COUNT, URL);

					return null;
				}
			});
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
