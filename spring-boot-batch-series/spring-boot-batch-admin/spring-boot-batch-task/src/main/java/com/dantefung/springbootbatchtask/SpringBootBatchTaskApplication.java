package com.dantefung.springbootbatchtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

/**
 * @EnableTask
 *
 * This class-level annotation tells Spring Cloud Task to bootstrap its functionality.
 * It enables a TaskConfigurer that registers the application in a TaskRepository.
 *
 *  Spring Cloud Task will also associate the execution of a batch job with a task’s execution
 *  so that one can be traced back to the other. This association is by default in any context
 *  that has both a Spring Batch Job configured and the spring-cloud-task-batch JAR available within the classpath.
 *
 *  Spring Cloud Task uses a datasource for storing the results of task executions.
 *  When running on Spring Cloud Data Flow we need to make sure that common datasource settings are shared among both.
 */
@EnableTask
@SpringBootApplication
public class SpringBootBatchTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBatchTaskApplication.class, args);
	}

}
