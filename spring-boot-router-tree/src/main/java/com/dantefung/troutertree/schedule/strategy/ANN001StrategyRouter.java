/*
 * Copyright (C), 2015-2018
 * FileName: ANN001StrategyRouter
 * Author:   DANTE FUNG
 * Date:     2020/10/22 21:48
 * Description: ANN001指令码处理路由
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 21:48   V1.0.0
 */
package com.dantefung.troutertree.schedule.strategy;

import com.dantefung.troutertree.annotation.Cmd;
import com.dantefung.troutertree.common.strategy.StrategyHandler;
import com.dantefung.troutertree.dto.AnnualCmdBody;
import com.dantefung.troutertree.dto.GenericCmdDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Title: ANN001StrategyRouter
 * @Description: ANN001指令码处理路由 无后继操作不继承AbstractStrategyRouter
 * @author DANTE FUNG
 * @date 2020/10/22 21:48
 */
@Slf4j
@Cmd(value = "ANN001", version = "1.0.0")
@Component
public class ANN001StrategyRouter implements StrategyHandler<GenericCmdDto<AnnualCmdBody>, Object> {

	@Override
	public StrategyHandler<GenericCmdDto<AnnualCmdBody>, Object> apply(GenericCmdDto<AnnualCmdBody> param) {
		String traceId = param.getCmdHeader().getTraceId();
		log.info(">>>>>>>>>>>traceId:{} {}接收{}执行本路由策略...",  traceId, ANN001StrategyRouter.class.getSimpleName(), param);
		return this;
	}
}
