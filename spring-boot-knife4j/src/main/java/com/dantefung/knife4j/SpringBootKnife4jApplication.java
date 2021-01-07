package com.dantefung.knife4j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class SpringBootKnife4jApplication {

	public static void main(String[] args) throws UnknownHostException {
		// http://localhost:8080/doc.html?plus=1
		log.info("Start~");
		ConfigurableApplicationContext application = SpringApplication.run(SpringBootKnife4jApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String applicationName = env.getProperty("spring.application.name");
		log.info("\n----------------------------------------------------------\n\t" +
				"Application " + applicationName + " is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + "/\n\t" +
				"swagger-ui: \thttp://" + ip + ":" + port + "/swagger-ui.html\n\t" +
				"Doc: \t\thttp://" + ip + ":" + port + "/doc.html?plus=1\n\n\t" );
	}

}
