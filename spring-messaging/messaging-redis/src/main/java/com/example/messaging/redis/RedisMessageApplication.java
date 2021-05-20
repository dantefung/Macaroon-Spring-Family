package com.example.messaging.redis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RedisMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisMessageApplication.class, args);
    }

    /**
     * 启动时发送消息
     *
     * @param template StringRedisTemplate
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner sendRedisMessage(RedisTemplate<?, ?> template) {
        return args -> template.convertAndSend(RedisMessageConfig.MESSAGE_BOX, new RedisMessage("test", "Hello redis!"));
    }


}
