package com.dantefung.springbootcache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@EnableCaching
@SpringBootApplication(scanBasePackages = { "com.dantefung.*" })
public class SpringBootCacheApplication {

	public static void main(String[] args) throws UnknownHostException {
		log.info("Start~");
		ConfigurableApplicationContext application = SpringApplication.run(SpringBootCacheApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String applicationName = env.getProperty("spring.application.name");
		log.info("\n----------------------------------------------------------\n\t" +
				"Application " + applicationName + " is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + "/\n\t" +
				"swagger-ui3.0: \thttp://" + ip + ":" + port + "/swagger-ui/index.html\n\t" +
				"Doc: \t\thttp://" + ip + ":" + port + "/doc.html\n\t" );
	}

}
