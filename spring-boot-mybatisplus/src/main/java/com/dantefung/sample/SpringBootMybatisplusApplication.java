package com.dantefung.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.dantefung.sample.**.mapper")
@SpringBootApplication
public class SpringBootMybatisplusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisplusApplication.class, args);
	}

}
