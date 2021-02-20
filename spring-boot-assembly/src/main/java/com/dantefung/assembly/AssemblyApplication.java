package com.dantefung.assembly;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class AssemblyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssemblyApplication.class, args);
	}


	@GetMapping
	public String hello() {
		log.info("hello world!");
		return "hello!";
	}

}
