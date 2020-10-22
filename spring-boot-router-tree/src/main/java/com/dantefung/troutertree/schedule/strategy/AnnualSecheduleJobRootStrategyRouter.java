/*
 * Copyright (C), 2015-2018
 * FileName: AnnualSecheduleJobRootStrategyRouter
 * Author:   DANTE FUNG
 * Date:     2020/10/22 17:51
 * Description: 年度排班执行策略的根路由节点
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 17:51   V1.0.0
 */
package com.dantefung.troutertree.schedule.strategy;

import com.dantefung.troutertree.common.strategy.AbstractStrategyRouter;
import com.dantefung.troutertree.common.strategy.StrategyHandler;
import com.dantefung.troutertree.common.utils.JsonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Title: AnnualSecheduleJobRootStrategyRouter
 * @Description: 年度排班执行策略的根路由节点
 * @author DANTE FUNG
 * @date 2020/10/22 17:51
 */
@Slf4j
@Component
public class AnnualSecheduleJobRootStrategyRouter extends AbstractStrategyRouter<String, Object> implements
		StrategyHandler<String, Object> {

	@Autowired
	private JsonParserStrategyRouter jsonParserStrategyRouter;
	@Autowired
	private NullParamStrategyRouter nullParamStrategyRouter;

	@Override
	protected StrategyMapper<String, Object> registerStrategyMapper() {
		/**
		 * 设计根路由的下级节点为协议解析层路由处理器
		 * 支持 xml 、json 格式的解析
		 * 参数由通过xxl-job传参.
		 */
		return param -> {
			StrategyHandler<String, Object> strategyHandler = null;
			if (JsonValidator.isJsonObject(param)) {
				strategyHandler = jsonParserStrategyRouter;
			}

			// 可扩展 xml 等格式的协议
			log.info(">>>>>>>>>>>> 路由下发: {}", Objects.nonNull(strategyHandler) ? strategyHandler.getClass().getSimpleName() : null);
			return strategyHandler;
		};
	}

	@Override
	public Object apply(String param) {
		StrategyHandler<String, Object> strategyHandler = null;
		if (StringUtils.isBlank(param)) {
			strategyHandler = nullParamStrategyRouter;
			return strategyHandler.apply(param);
		}
		log.info(">>>>>>>>>>>>>【根路由】下发至【协议解析层】 ....");
		return applyStrategy(param);
	}
}
