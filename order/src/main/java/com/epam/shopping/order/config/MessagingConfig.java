package com.epam.shopping.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Value("${spring.rabbitmq.host}")
    private String hostName;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtualhost}")
    private String virtualhost;

    @Value("${app.rabbitmq.order.exchange}")
    private String orderExchange;
    @Value("${app.rabbitmq.order.queue}")
    private String orderQueue;
    @Value("${app.rabbitmq.order.routingKey}")
    private String orderRoutingKey;

    //primary
    @Bean("orderQueue")
    public Queue orderQueue(){
        return QueueBuilder
                .durable(orderQueue)
                .deadLetterExchange(orderExchange)
                .deadLetterRoutingKey(orderRoutingKey + ".wait")
                .build();
    }
    @Bean("orderExchange")
    public Exchange orderExchange(){
        return ExchangeBuilder.topicExchange(orderExchange).build();
    }

    @Bean("orderBinding")
    public Binding orderBinding(){
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(orderRoutingKey).noargs();
    }
    // end primary

    // waiting
    @Bean("waitQueue")
    Queue orderWaitQueue() {
        return QueueBuilder.durable(orderQueue + ".wait")
                .deadLetterExchange(orderExchange)
                .deadLetterRoutingKey(orderRoutingKey)
                .ttl(1000)
                .build();
    }
    @Bean("orderWaitingBinding")
    public Binding orderWaitingBinding(){
        return BindingBuilder
                .bind(orderWaitQueue())
                .to(orderExchange())
                .with(orderRoutingKey + ".wait").noargs();
    }
    // end waiting





    // general configuration
    @Bean
    public AmqpTemplate template() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonConvertor());
        return rabbitTemplate;
    }
    @Bean
    public Jackson2JsonMessageConverter jsonConvertor(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(virtualhost);
        connectionFactory.setHost(hostName);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }
}
