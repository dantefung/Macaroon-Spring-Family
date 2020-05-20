/*
 * Copyright (C), 2015-2018
 * FileName: TransactionalServiceBeanBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/3/25 16:47
 * Description: 引导类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/25 16:47   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.bean.TransactionalServiceBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.SimpleTransactionStatus;

import java.util.Map;

/**
 * @Title: TransactionalServiceBeanBootstrap
 * @Description: 引导类
 * @author DANTE FUNG
 * @date 2020/3/25 16:47
 */
@Configuration
// 扫描 TransactionalServiceBean所在package
@ComponentScan(basePackageClasses = TransactionalServiceBean.class)
@EnableTransactionManagement // 激活事务管理器
public class TransactionalServiceBeanBootstrap {

	public static void main(String[] args) {
		// 注册当前引导类为Configuration class
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
				TransactionalServiceBeanBootstrap.class);
		// 获取所有TransactionServiceBean类型Bean.其中key为Bean名称
		Map<String, TransactionalServiceBean> beansMap = context.getBeansOfType(TransactionalServiceBean.class);
		beansMap.forEach((beanName, bean)->{
			System.out.printf("Bean 名称:%s, 对象: %s \n", beanName, bean);
			bean.save();
		});
		context.close();
	}

	@Bean("txManager")
	public PlatformTransactionManager txManager() {
		return new PlatformTransactionManager() {
			@Override
			public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
				return new SimpleTransactionStatus();
			}

			@Override
			public void commit(TransactionStatus status) throws TransactionException {
				System.out.println("txManager : 事务提交...");
			}

			@Override
			public void rollback(TransactionStatus status) throws TransactionException {
			}
		};
	}

	@Bean("txManager2")
	public PlatformTransactionManager txManger2() {
		return new PlatformTransactionManager() {
			@Override
			public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
				return new SimpleTransactionStatus();
			}

			@Override
			public void commit(TransactionStatus status) throws TransactionException {
				System.out.println("txManger2 : 事务提交...");
			}

			@Override
			public void rollback(TransactionStatus status) throws TransactionException {
			}
		};
	}
}
