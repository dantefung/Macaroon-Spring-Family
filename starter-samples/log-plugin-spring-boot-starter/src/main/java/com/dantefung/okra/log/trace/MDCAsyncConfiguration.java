package com.dantefung.okra.log.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * 通过实现AsyncConfigurer自定义异常线程池，包含异常处理
 *
 * @author DANTE FUNG
 *
 */
public class MDCAsyncConfiguration implements AsyncConfigurer {

	private static final Logger log = LoggerFactory.getLogger(MDCAsyncConfiguration.class);

	/**
	 * org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
	 * spring默认配置
	 * {
	 * 	"threadNamePrefix": "task-",
	 * 	"threadPoolExecutor": "java.util.concurrent.ThreadPoolExecutor@755ad3d4[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]",
	 * 	"corePoolSize": 8,
	 * 	"threadPriority": 5,
	 * 	"maxPoolSize": 2147483647,
	 * 	"keepAliveSeconds": 60,
	 * 	"daemon": false
	 * }
	 * @return
	 */
	@Primary
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new MDCThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("MDC-task-executor-");
		executor.setTaskDecorator(new MDCTaskDecorator());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

	@Override
	public Executor getAsyncExecutor() {
		Executor taskExecutor = taskExecutor();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new MyAsyncExceptionHandler();
	}

	/**
	 * 自定义异常处理类
	 * @author DANATE FUNG
	 *
	 */
	class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

		@Override
		public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
			log.info("Exception message - " + throwable.getMessage());
			log.info("Method name - " + method.getName());
			for (Object param : obj) {
				log.info("Parameter value - " + param);
			}
		}

	}

}