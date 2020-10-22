/*
 * Copyright (C), 2015-2018
 * FileName: ManualCompensationV100StrategyRouter
 * Author:   DANTE FUNG
 * Date:     2020/10/22 20:55
 * Description: 1.0.0版本人工补排班执行策略
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 20:55   V1.0.0
 */
package com.dantefung.troutertree.schedule.strategy;

import com.dantefung.troutertree.annotation.Cmd;
import com.dantefung.troutertree.common.strategy.AbstractStrategyRouter;
import com.dantefung.troutertree.common.strategy.StrategyHandler;
import com.dantefung.troutertree.dto.AnnualCmdBody;
import com.dantefung.troutertree.dto.GenericCmdDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Title: ManualCompensationV100StrategyRouter
 * @Description: 1.0.0版本人工补排班执行策略
 * @author DANTE FUNG
 * @date 2020/10/22 20:55
 */
@Slf4j
@Component
public class Version100ParserStrategyRouter extends AbstractStrategyRouter<GenericCmdDto<AnnualCmdBody>, Object>
		implements
		InitializingBean, StrategyHandler<GenericCmdDto<AnnualCmdBody>,Object> {

	private Map<String, StrategyHandler<GenericCmdDto<AnnualCmdBody>, Object>> cmdHolder = null;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Object apply(GenericCmdDto<AnnualCmdBody> param) {
		String traceId = param.getCmdHeader().getTraceId();
		log.info(">>>>>>>>>>>traceId:{} {}接收{}执行本路由策略...",traceId, Version100ParserStrategyRouter.class.getSimpleName(), param);
		// do nothing
		log.info(">>>>>>>>>>>>>traceId:{}【版本解析层】将下发至[指令解析层]...", traceId);
		return applyStrategy(param);
	}

	@Override
	protected StrategyMapper<GenericCmdDto<AnnualCmdBody>, Object> registerStrategyMapper() {
		return param -> {

			StrategyHandler<GenericCmdDto<AnnualCmdBody>, Object> strategyHandler = cmdHolder.get(param.getCmd());
			if (Objects.isNull(strategyHandler)) {
				log.info("======>traceId:{} 查无{}指令对应的处理路由...", param.getCmdHeader().getTraceId(), param.getCmd());
			} else {
				log.info("======>traceId:{} 指令码{}对应的处理路由为:{}", param.getCmdHeader().getTraceId(), param.getCmd(), strategyHandler.getClass().getSimpleName());
			}

			return strategyHandler;
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Cmd.class);
		cmdHolder = new HashMap<>();
		for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
			if (entry.getValue() instanceof StrategyHandler && entry.getValue().getClass().isAnnotationPresent(Cmd.class)) {
				StrategyHandler<GenericCmdDto<AnnualCmdBody>, Object> strategyHandler = (StrategyHandler) entry.getValue();
				String key = strategyHandler.getClass().getAnnotation(Cmd.class).value();
				String version = strategyHandler.getClass().getAnnotation(Cmd.class).version();
				if ("1.0.0".equals(version)) {
					cmdHolder.put(key, strategyHandler);
				}
			}
		}
	}
}
