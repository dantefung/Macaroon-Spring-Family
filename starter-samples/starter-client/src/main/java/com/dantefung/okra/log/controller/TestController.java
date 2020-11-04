package com.dantefung.okra.log.controller;

import com.dantefung.okra.log.annontation.LogTrace;
import com.dantefung.okra.log.annontation.SysLog;
import com.dantefung.okra.log.async.PersonManager;
import com.dantefung.okra.log.trace.MDCThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private PersonManager personManager;

	@SysLog("title is ok!")
	@LogTrace
	@RequestMapping("/ok")
	public String ok() {
		log.info("==========> enter {}.ok() method ...", this.getClass().getSimpleName());

		ThreadPoolExecutor pool = new MDCThreadPoolExecutor(3, 5,
				100, MILLISECONDS, new ArrayBlockingQueue<>(5));
		CompletableFuture.runAsync(() ->{

			log.info("===============> 子线程 ...");
		}, pool);

		personManager.printTaskExecutor();
		
		// 模拟异常
		personManager.sayError();

		return "ok!";
	}

	@RequestMapping("/trace")
	public String trace() {
		log.info("==========> enter {}.trace() method ...", this.getClass().getSimpleName());
		return "trace!";
	}
}
