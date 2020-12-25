/*
 * Copyright (C), 2015-2020
 * FileName: AppHolderConfig
 * Author:   DANTE FUNG
 * Date:     2020/12/24 16:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/24 16:35   V1.0.0
 */
package com.dantefung.freemarker.config;

import com.dantefung.freemarker.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: AppHolderConfig
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/24 16/35
 * @since JDK1.8
 */
@Configuration
public class AppHolderConfig implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.setAppContext(applicationContext);
	}
}