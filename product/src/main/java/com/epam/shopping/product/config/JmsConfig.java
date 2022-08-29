package com.epam.shopping.product.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Connection;
import javax.jms.Session;

@Configuration
@EnableJms
@ConfigurationProperties("application.properties")
public class JmsConfig {
    public static final String JMS_LISTENER_BEAN_NAME = "jmsListenerDefault";
    @Value("${spring.activemq.broker-url}") String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        factory.setTrustAllPackages(true);
    return factory;
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

    @Bean()
    public JmsTemplate template(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(false);
        return template;
    }

    @Bean()
    public Session session(){
        try {
            Connection connection = connectionFactory().createConnection();
            connection.start();
            return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {


        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
