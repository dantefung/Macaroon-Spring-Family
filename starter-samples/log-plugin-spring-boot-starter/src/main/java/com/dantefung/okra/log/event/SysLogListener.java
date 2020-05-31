package com.dantefung.okra.log.event;

import cn.hutool.json.JSONUtil;
import com.dantefung.okra.log.entity.SysLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;


/**
 * @author lengleng
 * 异步监听日志事件
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener {

	//private final RemoteLogService remoteLogService;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLog sysLog = (SysLog) event.getSource();
		log.info("------------>保存sysLog:{}到数据库!", JSONUtil.toJsonStr(sysLog));
		//remoteLogService.saveLog(sysLog, SecurityConstants.FROM_IN);
	}
}