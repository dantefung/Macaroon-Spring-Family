/*
 * Copyright (C), 2015-2018
 * FileName: ConditionalMessageConfiguration
 * Author:   DANTE FUNG
 * Date:     2020/5/20 15:58
 * Description: 实现"条件消息配置"
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/5/20 15:58   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.config;

import com.dantefung.thinkinginspringbootsamples.annotation.ConditionalOnSystemProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: ConditionalMessageConfiguration
 * @Description: 实现"条件消息配置"
 * @author DANTE FUNG
 * @date 2020/5/20 15:58
 */
@Configuration
public class ConditionalMessageConfiguration {

	@ConditionalOnSystemProperty(name = "language", value = "Chinese")
	@Bean("message") // Bean名称"message"的中文消息
	public String chineseMessage() {
		return "你好，世界!";
	}

	@ConditionalOnSystemProperty(name = "language", value = "English")
	@Bean("message") // Bean名称"message"的英文消息
	public String englishMessage() {
		return "Hello,World!";
	}
}
