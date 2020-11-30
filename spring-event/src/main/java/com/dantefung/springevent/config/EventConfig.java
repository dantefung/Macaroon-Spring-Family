/*
 * Copyright (C), 2015-2020
 * FileName: EventConfig
 * Author:   DANTE FUNG
 * Date:     2020/11/27 15:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/27 15:31   V1.0.0
 */
package com.dantefung.springevent.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Title: EventConfig
 * @Description:
 * @author DANTE FUNG
 * @date 2020/11/27 15/31
 * @since JDK1.8
 */
public class EventConfig {

	@Bean
	@LoadBalanced
	@ConditionalOnMissingBean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
