package com.dantefung.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class RocketmqProducerApplication {

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(RocketmqProducerApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		log.info("\n----------------------------------------------------------\n\t" +
				"Application local-producer is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + "/\n\t" +
				"swagger-ui: \thttp://" + ip + ":" + port + "/swagger-ui.html\n\t" +
				"Doc: \t\thttp://" + ip + ":" + port + "/doc.html\n\t");
	}

}
