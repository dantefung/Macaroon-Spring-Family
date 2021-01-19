package com.dantefung.springbootdcoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringBootDcokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDcokerApplication.class, args);
	}

	@GetMapping
	public String hello() {
		System.out.println("hello docker!");
		return "hello!";
	}

}
