package com.dantefung.okra.log.entity;

import lombok.Data;

@Data
public class SysLog {

	private String createBy;

	private String type;

	private String remoteAddr;

	private String requestUri;

	private String method;

	private String userAgent;

	private String params;

	private String serviceId;

	private String title;

	private long time;
}
