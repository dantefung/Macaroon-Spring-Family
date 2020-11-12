/*
 * Copyright (C), 2015-2020
 * FileName: LogInterceptorConfiguration
 * Author:   DANTE FUNG
 * Date:     2020/11/13 12:12 上午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/13 12:12 上午   V1.0.0
 */
package com.dantefung.okra.log.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import com.dantefung.okra.log.common.context.TLogContext;

/**
 * @Title: LogInterceptorConfiguration
 * @Description:
 * @author DANTE FUNG
 * @date 2020/11/13 00/12
 * @since JDK1.8
 */
@Slf4j
public class LogInterceptorConfiguration {

	@Bean
	public TLogWebConfig tLogWebConfig(){
		log.info("--------> 开始装配:{} ...", TLogWebConfig.class.getSimpleName());
		TLogContext.setHasTLogMDC(true);
		return new TLogWebConfig();
	}
}
