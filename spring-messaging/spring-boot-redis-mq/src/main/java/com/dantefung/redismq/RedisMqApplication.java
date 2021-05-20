package com.dantefung.redismq;

import com.dantefung.redismq.annotation.EnableRedisMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRedisMQ
@SpringBootApplication
public class RedisMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisMqApplication.class, args);
	}

}
