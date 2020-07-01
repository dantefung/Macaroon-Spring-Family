/*
 * Copyright (C), 2015-2018
 * FileName: MailPropertiesA
 * Author:   DANTE FUNG
 * Date:     2020/7/1 16:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/1 16:02   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.bean;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 感觉自己后知后觉，最近在思考，为什么小时候要阅读和背诵古诗词，文言文等经典，因为这样写文章就可以轻松熟练的引用经典。
 * 技术也一样，各种框架的源码就是学生时代的古诗词和文言文，我们要多多查看阅读，甚至背诵编程思想，这样就可以写出越来越优雅的代码
 *
 * @Title: MailPropertiesA
 * @Description:
 * @author DANTE FUNG
 * @date 2020/7/1 16:02
 */
@Data
@ToString
@ConfigurationProperties(prefix = "kaka.cream.mail-d",ignoreUnknownFields = false)
public class MailPropertiesD {

	private String hostName;
	private String name;
	private String sex;
	private Integer age;
	private List<String> stmpServers;
	/**
	 * 配置duration不写单位，默认按照毫秒来指定.
	 */
	@DurationUnit(ChronoUnit.SECONDS)
	private Duration pauseBetweenMails;
	/**
	 * 与 Duration 的用法一毛一样，默认单位是 byte (字节)，可以通过 @DataSizeUnit 单位指定
	 */
	@DataSizeUnit(DataUnit.MEGABYTES)
	private DataSize maxAttachementSize;
	/**
	 * 自定义类型
	 */
	private Weight maxAttachementWeight;

	/**
	 * 我们可以通过添加 @DeprecatedConfigurationProperty 注解到字段的 getter 方法上，来标示该字段为 deprecated
	 * @return
	 */
	@DeprecatedConfigurationProperty
	public Weight getMaxAttachementWeight() {
		return maxAttachementWeight;
	}
}
