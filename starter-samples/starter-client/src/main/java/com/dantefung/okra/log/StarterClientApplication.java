package com.dantefung.okra.log;

import com.dantefung.okra.log.annontation.EnableMdcAsync;
import com.dantefung.okra.log.annontation.EnableMdcTraceFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMdcTraceFilter
@EnableMdcAsync
@EnableScheduling
@SpringBootApplication
public class StarterClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarterClientApplication.class, args);
	}

}
