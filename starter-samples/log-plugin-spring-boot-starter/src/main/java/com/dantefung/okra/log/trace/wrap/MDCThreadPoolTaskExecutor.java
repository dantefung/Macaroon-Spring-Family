package com.dantefung.okra.log.trace.wrap;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class MDCThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	@Override
	public String toString() {
		return "MDCThreadPoolTaskExecutor{" + "corePoolSize=" + getCorePoolSize() + ", maxPoolSize=" + getMaxPoolSize()
				+ ", keepAliveSeconds=" + getKeepAliveSeconds() + ", threadNamePrefix='" + getThreadNamePrefix()+'}';
	}
}
