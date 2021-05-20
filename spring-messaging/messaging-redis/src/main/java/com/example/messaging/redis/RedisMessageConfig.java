package com.example.messaging.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * MessageConfig
 *
 * @author ben
 */
@Configuration
public class RedisMessageConfig {

    /**
     * 通道
     */
    public static final String MESSAGE_BOX = "messageBox";

    public static final String MESSAGE_BOX_ALL = "*";

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 监听两个通道
        container.addMessageListener(listenerAdapter, new PatternTopic(RedisMessageConfig.MESSAGE_BOX));
        container.addMessageListener(listenerAdapter, new PatternTopic(RedisMessageConfig.MESSAGE_BOX_ALL));

        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RedisMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, RedisMessageReceiver.RECEIVE_MESSAGE_METHOD);
    }

    /**
     * 消息接收对象
     *
     * @return 消息接收对象
     */
    @Bean
    public RedisMessageReceiver receiver() {
        return new RedisMessageReceiver();
    }

    /**
     * 自定义RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> template(RedisConnectionFactory connectionFactory,
                                                  Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer,
                                                  StringRedisSerializer stringRedisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // key 采用 String 的序列化方式
        template.setKeySerializer(stringRedisSerializer);

        // value 序列化方式采用 jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);

        // hash 的 key 也采用 String 的序列化方式,需要配置一下StringSerializer
        template.setHashKeySerializer(stringRedisSerializer);

        // hash 的 value 序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * jackson 序列化
     *
     * @return jackson 序列化对象
     */
    @Bean
    public Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        return jackson2JsonRedisSerializer;
    }

    /**
     * string 序列化
     *
     * @return string 序列化对象
     */
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

}
