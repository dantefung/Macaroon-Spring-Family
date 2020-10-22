/*
 * Copyright (C), 2015-2018
 * FileName: JsonParserStrategyRouter
 * Author:   DANTE FUNG
 * Date:     2020/10/22 18:04
 * Description: Json解析路由层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 18:04   V1.0.0
 */
package com.dantefung.troutertree.schedule.strategy;

import com.dantefung.troutertree.common.strategy.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Title: 定时器无参执行策略
 * @Description: 定时器无参执行策略
 * @author DANTE FUNG
 * @date 2020/10/22 18:04
 */
@Slf4j
@Component
public class NullParamStrategyRouter implements StrategyHandler<String, Object> {


	@Override
	public Object apply(String params) {
		log.info(">>>>>>>>>>>{}接收{}执行本路由策略...", NullParamStrategyRouter.class.getSimpleName(), params);
		return null;
	}
}
