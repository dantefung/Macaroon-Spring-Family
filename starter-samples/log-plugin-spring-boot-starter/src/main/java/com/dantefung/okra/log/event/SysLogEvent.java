package com.dantefung.okra.log.event;

import com.dantefung.okra.log.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author lengleng
 * 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}
}