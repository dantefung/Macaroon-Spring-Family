package com.dantefung.aop.springaopdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AmsSysLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 系统日志表主键
	 */
	private Long id;
	
	/**
	 * 产生日志的操作人员
	 */
	
	private Long userId;
	/**
	 * 产生的操作日志类型
	 */
	private String opeatorType;
	/**
	 * 操作人员具体的操作
	 */
	private String operatorContent;
	/**
	 * 操作的时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date operatorTime;

}
