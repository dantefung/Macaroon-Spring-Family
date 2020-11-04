package com.dantefung.sample.core.sql;

import com.alibaba.druid.pool.DruidDataSource;
import com.dantefung.sample.datasource.config.DynamicDataSourceFactory;
import com.dantefung.sample.datasource.properties.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateBuilder {

	private final String dbType;

	private final String driverClassName;

	private final String url;

	private final String username;

	private final String password;

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateBuilder(Builder builder) {
		this.dbType = builder.dbType;
		this.driverClassName = builder.driverClassName;
		this.url = builder.url;
		this.username = builder.username;
		this.password = builder.password;
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setDbType(this.dbType);
		dataSourceProperties.setDriverClassName(this.driverClassName);
		dataSourceProperties.setUrl(this.url);
		dataSourceProperties.setUsername(this.username);
		dataSourceProperties.setPassword(this.password);
		dataSourceProperties.setMaxWait(1000);
		dataSourceProperties.setBreakAfterAcquireFailure(true);
		dataSourceProperties.setConnectionErrorRetryAttempts(0);

		DruidDataSource druidDataSource = DynamicDataSourceFactory.buildDruidDataSource(dataSourceProperties);
		druidDataSource.setConnectionErrorRetryAttempts(0);
		this.jdbcTemplate = new JdbcTemplate(druidDataSource);
	}

	private JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}

	public static class Builder {

		private final String dbType;

		private final String driverClassName;

		private final String url;

		private final String username;

		private final String password;

		public Builder(String dbType, String driverClassName, String url, String username, String password) {
			this.dbType = dbType;
			this.driverClassName = driverClassName;
			this.url = url;
			this.username = username;
			this.password = password;
		}

		public JdbcTemplate build() {
			JdbcTemplateBuilder jdbcTemplateBuilder = new JdbcTemplateBuilder(this);
			return jdbcTemplateBuilder.jdbcTemplate();
		}
	}

}
