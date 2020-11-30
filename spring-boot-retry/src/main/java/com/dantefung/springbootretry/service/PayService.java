package com.dantefung.springbootretry.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.interceptor.MethodInvocationRetryCallback;
import org.springframework.retry.listener.MethodInvocationRetryListenerSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final int totalNum = 100000;

	/**
	 * The declarative approach to applying retry
	 * handling by using the @Retryable annotation shown earlier
	 * has an additional runtime dependency on AOP classes.
	 *
	 * For details on how to resolve this dependency in your project,
	 * see the 'Java Configuration for Retry Proxies' section.
	 * https://github.com/spring-projects/spring-retry#javaConfigForRetryProxies
	 *
	 * value 表示当有哪些异常的时候触发重试
	 * maxAttempts 表示最大重试次数为3
	 * delay 表示延迟重试时间
	 * multiplier 表示上一次延时时间是这一次的倍数。
	 * @param num
	 * @return
	 * @throws Exception
	 */
	@Retryable(value = Exception.class, maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5), listeners = {"methodRetryListener"})
	public int minGoodsnum(int num) throws Exception{
        logger.info("minGoodsnum开始"+ LocalTime.now());
        if(num <= 0){
            throw new Exception("数量不对");
        }
        logger.info("minGoodsnum执行结束");
        return totalNum - num;
    }

	@Recover
	public int recover(Exception e){
		logger.warn("减库存失败！！！");
		//记日志到数据库
		return totalNum;
	}

	@Component("methodRetryListener")
	public class MethodRetryListener extends MethodInvocationRetryListenerSupport {
		Map<String,String> monitoringTags = new HashMap<>();
		String labelTagName = "label";
		String classTagName = "class";
		String methodTagName = "method";
		@Override
		protected <T, E extends Throwable> void doClose(RetryContext context,
				MethodInvocationRetryCallback<T, E> callback, Throwable throwable) {
			monitoringTags.put(labelTagName, callback.getLabel());
			Method method = callback.getInvocation()
					.getMethod();
			monitoringTags.put(classTagName,
					method.getDeclaringClass().getSimpleName());
			monitoringTags.put(methodTagName, method.getName());

			// register a monitoring counter with appropriate tags
			// ...
		}

		@Override
		protected <T, E extends Throwable> void doOnError(RetryContext context,
				MethodInvocationRetryCallback<T, E> callback, Throwable throwable) {
			System.out.println("doOnError...");
		}


	}
}