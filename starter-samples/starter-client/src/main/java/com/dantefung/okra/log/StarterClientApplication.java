package com.dantefung.okra.log;

import com.dantefung.okra.log.annontation.EnableLogTraceInterceptor;
import com.dantefung.okra.log.annontation.EnableMdcAsync;
import com.dantefung.okra.log.annontation.EnableMdcTraceFilter;
import com.dantefung.okra.log.common.enhance.bytes.AspectLogEnhance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableLogTraceInterceptor
//@EnableMdcTraceFilter
@EnableMdcAsync
@EnableScheduling
@SpringBootApplication
public class StarterClientApplication {

	static {AspectLogEnhance.enhance();}

	public static void main(String[] args) {
		SpringApplication.run(StarterClientApplication.class, args);
	}

}
