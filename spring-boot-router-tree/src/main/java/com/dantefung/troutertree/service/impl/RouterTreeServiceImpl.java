/*
 * Copyright (C), 2015-2018
 * FileName: RouterTreeServiceImpl
 * Author:   DANTE FUNG
 * Date:     2020/10/23 1:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/23 1:53   V1.0.0
 */
package com.dantefung.troutertree.service.impl;

import com.dantefung.troutertree.schedule.strategy.AnnualSecheduleJobRootStrategyRouter;
import com.dantefung.troutertree.service.RouterTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: RouterTreeServiceImpl
 * @Description:
 * @author DANTE FUNG
 * @date 2020/10/23 1:53
 */
@Service
public class RouterTreeServiceImpl implements RouterTreeService {

	@Autowired
	private AnnualSecheduleJobRootStrategyRouter annualSecheduleJobRootStrategyRouter;

	@Override
	public void execute(String param) {
		annualSecheduleJobRootStrategyRouter.apply(param);
	}
}
