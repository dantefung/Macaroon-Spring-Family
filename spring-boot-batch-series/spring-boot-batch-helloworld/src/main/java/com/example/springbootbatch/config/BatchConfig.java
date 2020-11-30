package com.example.springbootbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

/**
 * 下面是@EnableBatchProcessing创建的概述:
 *
 * JobRepository (bean名称 "jobRepository")
 * JobLauncher (bean名称 "jobLauncher")
 * JobRegistry (bean名称 "jobRegistry")
 * JobExplorer (bean名称 "jobExplorer")
 * PlatformTransactionManager (bean名称 "transactionManager")
 * JobBuilderFactory (bean名称"jobBuilders")，它可以方便地防止您必须将作业存储库注入到每个Job(作业)中
 * StepBuilderFactory (bean名称 "stepBuilders")，以方便您避免将作业存储库和事务管理器注入到每个Step(步骤)中
 */
@Configuration
@EnableBatchProcessing// 它支持所有所需Spring Batch特性。它还提供了设置批处理作业的基本配置。
public class BatchConfig extends DefaultBatchConfigurer {

	/**
	 * 为了使Spring Batch使用基于 Map 的JobRepository，
	 * 我们需要扩展DefaultBatchConfigurer。
	 * 重写setDataSource()方法以不设置DataSource。
	 * 这将导致自动配置使用基于 Map 的JobRepository。
	 * @param dataSource
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		// initialize will use a Map based JobRepository (instead of database)
	}
}