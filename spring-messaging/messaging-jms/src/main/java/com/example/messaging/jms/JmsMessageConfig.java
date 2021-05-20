package com.example.messaging.jms;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

/**
 * jms 消息配置
 *
 * @author ben
 */
@Configuration
public class JmsMessageConfig {

    /**
     * 通道
     */
    public static final String MESSAGE_BOX = "messageBox";

    /**
     * 消息工厂
     */
    public static final String MESSAGE_FACTORY = "messageFactory";


    /**
     * Jms 监听容器工厂
     *
     * @param connectionFactory 连接工厂对象
     * @param configurer        默认配置
     * @return Jms 监听容器工厂
     */
    @Bean
    public JmsListenerContainerFactory<?> messageFactory(ConnectionFactory connectionFactory,
                                                         DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * 使用 TextMessage 序列化消息为 json
     *
     * @return 消息转换器
     */
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        // jms 只支持 TEXT 和 BYTES
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}
