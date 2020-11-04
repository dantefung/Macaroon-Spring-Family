package com.dantefung.okra.log.trace;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description: 装饰ThreadPoolExecutor，将父线程的MDC内容传给子线程
 * @Author: DANTE FUNG
 * @Date: 2020/11/4
 */
@Slf4j
public class MDCThreadPoolExecutor extends ThreadPoolExecutor {

	/**
	 * The default rejected execution handler
	 */
	private static final RejectedExecutionHandler defaultHandler =
			new AbortPolicy();

	/**
	 * @Description:
	 * @param corePoolSize: 核心线程池大小
	 * @param maximumPoolSize: 最大线程池大小
	 * @param keepAliveTime: 线程最大空闲时间
	 * @param unit: 时间单位
	 * @param workQueue: 线程等待队列
	 * @return:
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/4
	 */
	public MDCThreadPoolExecutor(int corePoolSize,
			int maximumPoolSize,
			long keepAliveTime,
			TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new NamedThreadFactory("MDC-executor"), defaultHandler);
	}

	/**
	 * @Description:
	 * @param corePoolSize: 核心线程池大小
	 * @param maximumPoolSize: 最大线程池大小
	 * @param keepAliveTime: 线程最大空闲时间
	 * @param unit: 时间单位
	 * @param workQueue: 线程等待队列
	 * @param threadFactory: 线程创建工厂
	 * @return:
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/4
	 */
	public MDCThreadPoolExecutor(int corePoolSize,
			int maximumPoolSize,
			long keepAliveTime,
			TimeUnit unit,
			BlockingQueue<Runnable> workQueue,
			ThreadFactory threadFactory) {
		this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory == null ? new NamedThreadFactory("MDC-executor") : threadFactory, defaultHandler);
	}



	/**
	 * @Description:
	 * @param corePoolSize: 核心线程池大小
	 * @param maximumPoolSize: 最大线程池大小
	 * @param keepAliveTime: 线程最大空闲时间
	 * @param unit: 时间单位
	 * @param workQueue: 线程等待队列
	 * @param threadFactory: 线程创建工厂
	 * @param handler: 拒绝策略
	 * @return:
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/4
	 */
	public MDCThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	@Override
	public void execute(final Runnable runnable) {
		// 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
		final Map<String, String> context = MDC.getCopyOfContextMap();
		super.execute(()->{
			// 将父线程的MDC内容传给子线程
			MDC.setContextMap(context);
			try {
				// 执行异步操作
				runnable.run();
			} finally {
				// 清空MDC内容
				MDC.clear();
			}
		});
	}
}