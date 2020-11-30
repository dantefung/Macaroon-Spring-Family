package com.example.springbootbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * spring-boot-starter-batch starter依赖于spring-boot-starter-jdbc，并将尝试实例化数据源。
 * 添加 exclude = {DataSourceAutoConfiguration.class}注解中添加@SpringBootApplication。
 * 这可以防止 Spring Boot 为数据库连接自动配置DataSource。
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBatchApplication.class, args);
	}

}
