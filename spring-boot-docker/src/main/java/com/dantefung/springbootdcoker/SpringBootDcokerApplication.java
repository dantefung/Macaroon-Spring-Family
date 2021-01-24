package com.dantefung.springbootdcoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

	@GetMapping("/ride")
	public Object ride() {
		System.out.println("ride...");
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("code", 200);
		map.put("data", Arrays.asList(1,2,3,4,5,6,7,8));
		return map;
	}

}
