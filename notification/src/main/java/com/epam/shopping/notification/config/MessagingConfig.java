package com.epam.shopping.notification.config;

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

    // failed
    @Bean("failedOrderQueue")
    public Queue failedOrderQueue(){
        return QueueBuilder.durable(orderQueue+".failed").build();
    }

    @Bean("failedOrderExchange")
    public Exchange failedOrderExchange(){
        return ExchangeBuilder.topicExchange(orderExchange + ".failed").build();
    }


    @Bean("failedOrderBinding")
    public Binding failedOrderBinding(){
        return BindingBuilder.bind(failedOrderQueue()).to(failedOrderExchange()).with(orderRoutingKey + ".failed").noargs();
    }
    // end failed


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
