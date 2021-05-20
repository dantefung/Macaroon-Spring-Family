package com.example.message.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMqMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqMessageApplication.class, args);
    }

    /**
     * 启动时发送消息
     *
     * @param rabbitTemplate rabbitTemplate
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner sendRabbitMqMessage(RabbitTemplate rabbitTemplate) {
        return args -> rabbitTemplate.convertAndSend(
                RabbitMqMessageConfig.EXCHANGE_NAME,
                "test.foo",
                new RabbitMqMessage("test", "Hello RabbitMQ!"));

    }

}
