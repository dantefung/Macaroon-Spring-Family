/*
 * Copyright (C), 2015-2018
 * FileName: ServerImportBeanDefinitionRegistrar
 * Author:   DANTE FUNG
 * Date:     2020/3/27 20:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/27 20:26   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.sample.server.impl;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.stream.Stream;

/**
 * @Title: ServerImportBeanDefinitionRegistrar
 * @Description:
 * @author DANTE FUNG
 * @date 2020/3/27 20:26
 */
public class ServerImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// 复用{@link ServerImportSelector}实现，避免重复劳动
		ImportSelector importSelector = new ServerImportSelector();
		// 筛选Class名称集合
		String[] selectedClassNames = importSelector.selectImports(importingClassMetadata);
		// 创建Bean定义
		Stream.of(selectedClassNames)
				// 转化为BeanDefinitionBuilder对象
				.map(BeanDefinitionBuilder::genericBeanDefinition)
				// 转化为BeanDefinition
				.map(BeanDefinitionBuilder::getBeanDefinition)
				.forEach(beanDefinition -> {
					// 注册BeanDefinition到BeanDefinitionRegistry
					BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
				});
	}


}
