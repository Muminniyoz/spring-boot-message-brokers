package com.epam.shopping.product.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
@ConfigurationProperties("application.properties")
public class JmsConfig {
    public static final String JMS_LISTENER_BEAN_NAME = "jmsListenerDefault";


    @Bean
    public ActiveMQConnectionFactory connectionFactory(@Value("${spring.activemq.broker-url}") String brokerUrl) {
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    @Bean(JMS_LISTENER_BEAN_NAME)
    public DefaultJmsListenerContainerFactory containerFactory(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonJmsMessageConverter());

        // durable subsciption
//        factory.setSubscriptionDurable(true);
//        factory.setClientId("clientid2");
        //

        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {


        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
