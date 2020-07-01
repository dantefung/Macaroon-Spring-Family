/*
 * Copyright (C), 2015-2018
 * FileName: PropConfiguration
 * Author:   DANTE FUNG
 * Date:     2020/7/1 16:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/1 16:05   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.config;

import com.dantefung.thinkinginspringbootsamples.bean.MailPropertiesB;
import com.dantefung.thinkinginspringbootsamples.bean.MailPropertiesC;
import com.dantefung.thinkinginspringbootsamples.bean.MailPropertiesD;
import com.dantefung.thinkinginspringbootsamples.bean.WeightConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: PropConfiguration
 * @Description: https://segmentfault.com/a/1190000020183307
 * @author DANTE FUNG
 * @date 2020/7/1 16:05
 */
@EnableConfigurationProperties({MailPropertiesC.class, MailPropertiesD.class})
@Configuration
public class PropConfiguration {

	/**
	 * 在配置类中进行装配,这两个注解均出现在Configuration中，对POJO无侵入，使用灵活，且集中（均在配置类中处理）
	 *
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "kaka.cream.mail-b",ignoreUnknownFields = false)
	public MailPropertiesB mailPropertiesB(){
		MailPropertiesB b = new MailPropertiesB();
		return b;
	}

	@Bean
	@ConfigurationPropertiesBinding
	public WeightConverter weightConverter() {
		return new WeightConverter();
	}
}


