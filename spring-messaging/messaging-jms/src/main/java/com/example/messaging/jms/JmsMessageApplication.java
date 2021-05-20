package com.example.messaging.jms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class JmsMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsMessageApplication.class, args);
    }

    /**
     * 启动时发送消息
     *
     * @param jmsTemplate JmsTemplate
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner sendJmsMessage(JmsTemplate jmsTemplate) {
        return args -> {
            JmsMessage message = new JmsMessage("test", "Hello Jms!");
            System.out.println("Send<" + message + ">");
            jmsTemplate.convertAndSend(JmsMessageConfig.MESSAGE_BOX, message);
        };
    }

}
