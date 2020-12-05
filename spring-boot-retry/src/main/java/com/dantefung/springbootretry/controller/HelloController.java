/*
 * Copyright (C), 2015-2020
 * FileName: HelloController
 * Author:   DANTE FUNG
 * Date:     2020/11/27 10:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/27 10:23   V1.0.0
 */
package com.dantefung.springbootretry.controller;

import com.dantefung.springbootretry.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.ThreadWaitSleeper;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: HelloController
 * @Description: 看着github上spring-retry的readme.md过一下api
 * 参考:
 *   https://blog.csdn.net/broadview2006/article/details/72841056
 *   https://github.com/spring-projects/spring-retry
 * @author DANTE FUNG
 * @date 2020/11/27 10/23
 * @since JDK1.8
 */
@Slf4j
@RestController
@RequestMapping("/")
public class HelloController {

	@Autowired
	private PayService payService;

	/**
	 * @Description: Declarative Example
	 * @param num:
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/27 10:40
	 * @since JDK 1.8
	 * @return: java.lang.String
	 */
	@GetMapping("/createOrder")
	public String createOrder(@RequestParam int num) throws Exception {
		int remainingnum = payService.minGoodsnum(num == 0 ? 1 : num);
		log.info("剩余的数量===" + remainingnum);
		return "库库存成功";
	}

	// 实现类正常方法
	// openTimeout时间范围内失败maxAttempts次数后，熔断打开resetTimeout时长
	@GetMapping("/breaker")
	@CircuitBreaker(maxAttempts = 3, openTimeout = 3000L, resetTimeout = 5000L )
	public String normalMethod(String param) {
		log.info("======== normalMethod ======== " + param);
		if (true) {
			throw new RuntimeException("方法异常");
		}
		return param;
	}

	// 降级方法
	@Recover
	public String recoverMethod(Throwable t, String param) {
		log.info("======== recoverMethod ======== " + param);
		return param;
	}


	/**
	 * 参见： https://github.com/spring-projects/spring-retry
	 * @Description: Imperative Example
	 * Since version 1.3, fluent configuration of RetryTemplate is also available, as follows:
	 *
	 * RetryTemplate.builder()
	 *       .maxAttempts(10)
	 *       .exponentialBackoff(100, 2, 10000)
	 *       .retryOn(IOException.class)
	 *       .traversingCauses()
	 *       .build();
	 *
	 * RetryTemplate.builder()
	 *       .fixedBackoff(10)
	 *       .withinMillis(3000)
	 *       .build();
	 *
	 * RetryTemplate.builder()
	 *       .infiniteRetry()
	 *       .retryOn(IOException.class)
	 *       .uniformRandomBackoff(1000, 3000)
	 *       .build();
	 *
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/27 10:43
	 * @since JDK 1.8		
	 * @return:
	 */
	@GetMapping("/imperative")
	public String imperative() {
		RetryTemplate template = RetryTemplate.builder().maxAttempts(3).fixedBackoff(1000)
				//.retryOn(RemoteAccessException.class)
				.retryOn(Exception.class).build();
		// lambda
		template.execute(ctx -> {
			log.info("重试开始: {}", LocalTime.now());
			int i = 1 / 0;
			return null;
		});
		return "ok!";
	}

	// ---------------------------------------- doWithRetry  ------------------------------------------

	@GetMapping("/doWithRetry")
	public String doWithRetry() throws Exception {
		// There are a number of overloaded execute methods in the RetryOperations interface,
		// to deal with various use cases for recovery when all retry attempts are exhausted and to deal with retry state,
		// which lets clients and implementations store information between calls (more on this later).
		// >>>>>>The simplest general purpose implementation of RetryOperations is RetryTemplate.<<<<<<
		RetryTemplate template = new RetryTemplate();
		TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
		// In the preceding example, we execute a web service call and return the result to the user.
		// If that call fails, it is retried until a timeout is reached.
		// 设置30秒试试
		policy.setTimeout(30000L);// milliseconds
		template.setRetryPolicy(policy);
		MyBizLogicHandler myBizLogicHandler = new MyBizLogicHandler();
		template.execute(myBizLogicHandler,
				// RetryTemplate throws the original exception, except in the stateful case, when no recovery is available.
				new RecoveryCallback<Void>() {
					public Void recover(RetryContext context) throws Exception {
						// recover logic here
						log.info("呀~ 搬砖砸到脚了!! try to recovery ... hiahiahia... -A-\\\\");
						// 这里的最佳实践是用来返回假数据或者托底的数据，或者进行错误结构化日志记录等
						return null;
					}
				});
		return "ok!";
	}


	/**
	 * 参见: https://github.com/spring-projects/spring-retry
	 * 为业务处理器增加重试的能力
	 *
	 * The callback is tried, and, if it fails (by throwing an Exception),
	 * it is retried until either it is successful or the implementation decides to abort.
	 *
	 * The basic callback is a simple interface that lets you insert some business logic to be retried:
	 */
	class MyBizLogicHandler implements RetryCallback<Void, Exception> {

		@Override
		public Void doWithRetry(RetryContext retryContext) throws Exception {
			// The method parameter for the RetryCallback is a RetryContext. Many callbacks ignore the context.
			// However, if necessary, you can use it as an attribute bag to store data for the duration of the iteration.
			if (retryContext.getLastThrowable() != null) {
				log.error("", retryContext.getLastThrowable());
			}
			log.info("第{}次 重试逻辑:{}", retryContext.getRetryCount() + 1, "呜喵~");
			// business logic here
			log.info("努力搬砖中...");
			int i = 1 / 0;
			return null;
		}
	}

	// ---------------------------------------- doWithRetry  ------------------------------------------

	/**
	 *
	 * 访问:
	 *    http://localhost:8080/retryPolicy?exType=NPE
	 *    http://localhost:8080/retryPolicy?exType=INDEXOUT
	 *    http://localhost:8080/retryPolicy?exType=AE
	 *
	 *
	 * 参见：https://github.com/spring-projects/spring-retry
	 * A more flexible implementation called ExceptionClassifierRetryPolicy is also available.
	 * It lets you configure different retry behavior for an arbitrary set of exception types through the ExceptionClassifier abstraction.
	 * The policy works by calling on the classifier to convert an exception into a delegate RetryPolicy.
	 * For example, one exception type can be retried more times before failure than another, by mapping it to a different policy.
	 *
	 * You might need to implement your own retry policies for more customized decisions.
	 * For instance, if there is a well-known, solution-specific, classification of exceptions into retryable and not retryable.
	 *
	 * 更加灵活的实现为ExceptionClassifierRetryPolicy，也是可用的。
	 * 通过ExceptionClassifier的抽象，对于一个任意的异常类型集，它能让你配置不同的重试行为，
	 * 该策略通过classifier的调用来工作，以达到转换一个异常到一个委托的RetryPolicy
	 *
	 * 不翻译了 -A-\\\
	 *
	 * @return
	 */
	@GetMapping("/retryPolicy")
	public String testRetryPolicy(String exType) {
		ExceptionClassifierRetryPolicy classifierRetryPolicy = new ExceptionClassifierRetryPolicy();
		Map<Class<? extends Throwable>, RetryPolicy> policyMap = new HashMap<>();
		// 查看RetryPolicy接口有挺多实现，我们选取其中几个来做个小示例.
		// 数组越界异常, 重试策略: 放弃治疗
		NeverRetryPolicy neverRetryPolicy = new NeverRetryPolicy();
		policyMap.put(ArrayIndexOutOfBoundsException.class, neverRetryPolicy);

		// 算数异常，重试策略: 3次
		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
		simpleRetryPolicy.setMaxAttempts(3);// 最多重试3次
		policyMap.put(ArithmeticException.class, simpleRetryPolicy);

		// NPE异常，重试策略: 10s内一直重试
		TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
		timeoutRetryPolicy.setTimeout(10000L);// 单位: 毫秒
		policyMap.put(NullPointerException.class, timeoutRetryPolicy);


		// RuntimeException.class , 重试策略: 熔断机制  也有注解实现: @CircuitBreaker
		// 有状态重试，有两种情况需要使用有状态重试，事务操作需要回滚或者熔断器模式。 todo: 深入理解
		//		CircuitBreakerRetryPolicy circuitBreakerRetryPolicy = new CircuitBreakerRetryPolicy(new SimpleRetryPolicy(3));
		//		circuitBreakerRetryPolicy.setOpenTimeout(5000);// 5秒 单位: 毫秒
		//		circuitBreakerRetryPolicy.setResetTimeout(20000);// 20秒 单位: 毫秒
		//		policyMap.put(RuntimeException.class, circuitBreakerRetryPolicy);


		classifierRetryPolicy.setPolicyMap(policyMap);
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(classifierRetryPolicy);

		// 重试时的退避策略是什么？是立即重试还是等待一段时间后重试，比如是网络错误，立即重试将导致立即失败，
		// 最好等待一小段时间后重试，还要防止很多服务同时重试导致 DDos。
		// 参见微信支付的文档，里边就有很好的实践: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7&index=8
		// 原文如下:
		// 2、后台通知交互时，如果微信收到商户的应答不符合规范或超时，微信会判定本次通知失败，重新发送通知，直到成功为止（在通知一直不成功的情况下，
		// 微信总共会发起多次通知，通知频率为15s/15s/30s/3m/10m/20m/30m/30m/30m/60m/3h/3h/3h/6h/6h - 总计 24h4m），但微信不保证通知最终一定能成功。
		// 参见：https://github.com/spring-projects/spring-retry#backoff-policies
		// 原文如下:
		// A BackoffPolicy is free to implement the backoff in any way it chooses.
		// 可以选择任意方式实现退避策略
		// The policies provided by Spring Retry all use Object.wait().
		// 这些策略由Spring Retry所提供，他们均使用Object.wait()方法
		// A common use case is to back off with an exponentially increasing wait period, ---> 划重点，微信支付通知就很好的实现了指数增长的等待周期这种退避策略
		// to avoid two retries getting into lock step and both failing (a lesson learned from Ethernet).
		// For this purpose, Spring Retry provides ExponentialBackoffPolicy.
		// Spring Retry also provides randomized versions of delay policies that are quite useful
		// to avoid resonating between related failures in a complex system.
		//退避策略：指数退避策略
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		//backOffPolicy.setInitialInterval(100);// 0.1秒 默认100毫秒
		backOffPolicy.setInitialInterval(2000);// 2秒，为了控制台能更加明显看到退避策略效果.
		backOffPolicy.setMaxInterval(3000);// 3秒
		backOffPolicy.setMultiplier(2);// 倍数
		backOffPolicy.setSleeper(new ThreadWaitSleeper());
		retryTemplate.setBackOffPolicy(backOffPolicy);

		/*Map<String,String> monitoringTags = new HashMap<>();
		String labelTagName = "label";
		String classTagName = "class";
		String methodTagName = "method";
		retryTemplate.registerListener(new MethodInvocationRetryListenerSupport() {
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
		});*/

		// 当重试失败后, 抛出异常
		/*retryTemplate.execute(ctx -> {
			log.info("第{}次，触发{}的重试策略 ...", ctx.getRetryCount() + 1, ctx.getLastThrowable());
			if ("NPE".equalsIgnoreCase(exType)) {
				String npe = null;
				npe.toString();
			} else if ("INDEXOUT".equalsIgnoreCase(exType)) {
				List list = new ArrayList<>(2);
				list.get(2);
			} else if ("AE".equalsIgnoreCase(exType)) {
				int i = 1 / 0;
			}
			return null;
		});*/
		// 当重试失败后，执行RecoveryCallback
		retryTemplate.execute(ctx -> {
			log.info("{}秒  第{}次，触发{}的重试策略 ...", LocalTime.now().getSecond(), ctx.getRetryCount() + 1,
					ctx.getLastThrowable());
			if ("NPE".equalsIgnoreCase(exType)) {
				String npe = null;
				npe.toString();
			} else if ("INDEXOUT".equalsIgnoreCase(exType)) {
				List list = new ArrayList<>(2);
				list.get(2);
			} else if ("AE".equalsIgnoreCase(exType)) {
				int i = 1 / 0;
			}
			return null;
		}, new RecoveryCallback<String>() {
			@Override
			public String recover(RetryContext context) throws Exception {
				log.info("  执行兜底方法 ... ");
				return "default";
			}
		});

		return "ok!";
	}


}
