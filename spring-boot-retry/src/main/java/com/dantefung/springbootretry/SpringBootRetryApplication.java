package com.dantefung.springbootretry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @Description: 参见: https://github.com/spring-projects/spring-retry
 * @Author: DANTE FUNG
 * @Date: 2020/11/27 10:39
 * @since JDK 1.8
 * @return:
 */
@EnableRetry
@SpringBootApplication
public class SpringBootRetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRetryApplication.class, args);
	}

}
