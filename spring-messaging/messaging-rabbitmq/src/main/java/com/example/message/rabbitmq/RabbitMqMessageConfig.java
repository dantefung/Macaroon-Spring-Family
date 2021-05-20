package com.example.message.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MessageConfig
 *
 * @author ben
 */
@Configuration
public class RabbitMqMessageConfig {

    /**
     * 定义 exchange
     */
    public static final String EXCHANGE_NAME = "test-exchange";

    /**
     * 定义队列名称
     */
    public static final String QUEUE_NAME = "test-queue";

    /**
     * 路由键
     */
    public static final String ROUTING_KEY = "test.#";

    /**
     * 消息队列
     *
     * @return 消息队列
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * 消息交换机
     *
     * @return 消息交换机
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * 消息队列与消息交换机绑定
     *
     * @param queue    消息队列
     * @param exchange 消息交换机
     * @return 绑定结果
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
     * 消息监听容器
     *
     * @param connectionFactory 连接工厂对象
     * @param listenerAdapter   消息监听适配器
     * @return 消息监听容器
     */
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    /**
     * 消息监听适配器
     *
     * @param receiver 消息接听对象
     * @return 消息监听适配器
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RabbitMqMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, RabbitMqMessageReceiver.RECEIVE_MESSAGE_METHOD);
    }

    /**
     * 消息接收对象
     *
     * @return 消息接对象
     */
    @Bean
    public RabbitMqMessageReceiver receiver() {
        return new RabbitMqMessageReceiver();
    }

}
