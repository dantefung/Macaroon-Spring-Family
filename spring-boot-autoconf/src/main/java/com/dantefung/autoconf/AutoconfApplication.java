package com.dantefung.autoconf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class AutoconfApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoconfApplication.class, args);
    }

}
