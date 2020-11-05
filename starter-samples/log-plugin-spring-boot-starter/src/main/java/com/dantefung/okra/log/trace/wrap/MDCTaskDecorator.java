package com.dantefung.okra.log.trace.wrap;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MDCTaskDecorator implements TaskDecorator {
	@Override
	public Runnable decorate(Runnable runnable) {
		Map<String, String> contextMap = MDC.getCopyOfContextMap();
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				if (contextMap != null) {
					MDC.setContextMap(contextMap);
				}
				runnable.run();
			}
		};
		return runnable1;
	}
}