/*
 * Copyright (C), 2015-2020
 * FileName: GoodService
 * Author:   DANTE FUNG
 * Date:     2021/3/4 10:11 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/4 10:11 下午   V1.0.0
 */
package com.dantefung.sample.aware;

import com.dantefung.beans.BeansException;
import com.dantefung.beans.factory.BeanFactory;
import com.dantefung.beans.factory.BeanFactoryAware;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: GoodService
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/04 22/11
 * @since JDK1.8
 */
@Slf4j
public class GoodService implements BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		log.info("BeanFactoryAware -> beanFacotry: {}", beanFactory);
	}
}
