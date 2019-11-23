package com.dantefung.aop.springaopdemo.props;

import com.dantefung.aop.springaopdemo.enums.LogLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 日志配置
 *
 * @author L.cm
 */
@Getter
@Setter
@Component
@ConfigurationProperties(LogLevel.REQ_LOG_PROPS_PREFIX)
public class RequestLogProperties {

	/**
	 * 日志级别配置，默认：BASIC
	 */
	private LogLevel level = LogLevel.BASIC;
}