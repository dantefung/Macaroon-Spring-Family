/*
 * Copyright (C), 2015-2020
 * FileName: ApplyService
 * Author:   DANTE FUNG
 * Date:     2021/3/4 10:23 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/4 10:23 下午   V1.0.0
 */
package com.dantefung.initializing;

import com.dantefung.beans.factory.InitializingBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: ApplyService
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/04 22/23
 * @since JDK1.8
 */
@Slf4j
public class ApplyService implements InitializingBean {

	public void init() {
		log.info("xml init-method ...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("InitializingBean -> do some logic ...");
	}
}
