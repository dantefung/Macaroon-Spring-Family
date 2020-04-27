package com.dantefung.aop.springaopdemo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 应用事件日志表
 * @Author: jeecg-boot
 * @Date:   2020-02-19
 * @Version: V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AppsysEventLog {
    
	/**UUID(32位)*/
	private String id;
	/**事件源应用系统id*/
	private String originAppSysId;
	/**事件编码（英文），与app_sys_id加起来唯一，eg: VALIDATE_TOKEN*/
	private String eventCode;
	/**事件时间*/
	private Date eventTime;
	/**采集类型（1：后台采集，2：前端采集）*/
	private Integer collectType;
	/**事件关联的用户id*/
	private String userId;
	/**事件关联的登录账号（英文）*/
	private String loginAcc;
	/**事件日志文本*/
	private Object eventMsg;
	/** 事件来源的应用url域名(ip)*/
	private String eventHost;
	/** 事件关联URL*/
	private String eventUrl;
	/**事件处理前输入数据*/
	private Object eventDataInput;
	/**事件处理后输出数据*/
	private Object eventDataOutput;
	/**备注*/
	private Object memo;
	/**创建发送任务的时间（未发送状态）*/
	private Date createTime;
	/**创建消息发送任务的操作人员（登录账号）*/
	private String createBy;
	/**更新时间*/
	private Date updateTime;
	/**更新者（更新账号）*/
	private String updateBy;
	/**是否删除（0：否； 非0，自增：是，结合其他字段进行唯一索引）*/
	private Integer delFlag;
}
