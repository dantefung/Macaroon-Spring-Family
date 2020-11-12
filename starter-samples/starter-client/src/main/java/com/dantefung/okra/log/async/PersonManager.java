package com.dantefung.okra.log.async;

import com.dantefung.okra.log.annontation.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Slf4j
@Component
public class PersonManager {

	@Autowired
	private Executor executor;

	@Async
	public void sayError(){
		log.info("======> hello!");
		int i = 1/0;
	}

	public void printTaskExecutor() {
		log.info("======>{}",executor);
	}

	/**
	 * 带参数的异步调用 异步方法可以传入参数
	 *  对于返回值是void，异常会被AsyncUncaughtExceptionHandler处理掉
	 * @param s
	 */
	@Async
	public void asyncInvokeWithException(String s) {
		log.info("asyncInvokeWithParameter, parementer={}", s);
		throw new IllegalArgumentException(s);
	}

	/**
	 * 异常调用返回Future
	 *  对于返回值是Future，不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
	 *  或者在调用方在调用Futrue.get时捕获异常进行处理
	 *
	 * @param i
	 * @return
	 */
	@Async
	public Future<String> asyncInvokeReturnFuture(int i) {
		log.info("asyncInvokeReturnFuture, parementer={}", i);
		Future<String> future;
		try {
			Thread.sleep(1000 * 1);
			future = new AsyncResult<String>("success:" + i);
			throw new IllegalArgumentException("a");
		} catch (InterruptedException e) {
			future = new AsyncResult<String>("error");
		} catch(IllegalArgumentException e){
			future = new AsyncResult<String>("error-IllegalArgumentException");
		}
		return future;
	}

	// 推荐像定时器这样的用@LogTrace
	@LogTrace
	@Scheduled(cron = "0/5 * * * * ?")
	public void testScheduled() {
		log.info(" scheduling ...");
	}

	public void testCallable() {

	}

}
