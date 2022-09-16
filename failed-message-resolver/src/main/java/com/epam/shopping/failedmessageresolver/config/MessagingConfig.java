package com.epam.shopping.failedmessageresolver.config;

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
