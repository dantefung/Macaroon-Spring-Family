package com.dantefung.aop.springaopdemo;

import cn.yueshutong.springprojecttree.config.annotation.EnableProjectTree;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProjectTree("execution(* com.dantefung.aop.springaopdemo..*(..))")
@SpringBootApplication
public class SpringAopDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAopDemoApplication.class, args);
	}

}
