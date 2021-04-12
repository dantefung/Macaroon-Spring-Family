/*
 * Copyright (C), 2015-2020
 * FileName: DataSourceConfig
 * Author:   DANTE FUNG
 * Date:     2021/3/29 15:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/29 15:08   V1.0.0
 */
package com.dantefung.tx.multidstx.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dantefung.xs.boot.dynamic.datasource.config.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Title: DataSourceConfig
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/29 15/08
 * @since JDK1.8
 */
@Slf4j
@Configuration
public class DataSourceConfig {

/*	@Bean
	public JdbcTemplate jdbcTemplate(DynamicDataSource dynamicDataSource) {
		return new JdbcTemplate(dynamicDataSource);
	}*/

	@Bean
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource2) {
		return new JdbcTemplate(dataSource2);
	}

	@Bean("dataSource")
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		druidDataSource.setUrl("jdbc:mysql://localhost:3306/db_test?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true");
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("root");
		druidDataSource.setInitialSize(5);
		return druidDataSource;
	}

	@Bean("dataSource2")
	public DataSource dataSource2() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		druidDataSource.setUrl("jdbc:mysql://localhost:3306/db_test2?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true");
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("root");
		druidDataSource.setInitialSize(5);
		return druidDataSource;
	}

	@Bean("transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean("tansactionManager2")
	public PlatformTransactionManager transactionManager2(@Qualifier("dataSource2") DataSource dataSource2) {
		log.info(" >>>>>>>>>> {}", dataSource2);
		return new DataSourceTransactionManager(dataSource2);
	}
}
