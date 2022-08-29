package com.epam.shopping.order.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

@Configuration
@EnableJms
public class JmsConfig {
    public static final String JMS_TEMPLATE_TOPIC_BEAN_NAME ="topicJmsTemplate";
    @Value("${spring.activemq.broker-url}") String brokerUrl;
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    @Bean
    @Qualifier(JMS_TEMPLATE_TOPIC_BEAN_NAME)
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory factory){
        JmsTemplate template = new JmsTemplate(factory);
        template.setPubSubDomain(false);
        template.setMessageConverter(jacksonJmsMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {


        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
    @Bean()
    public Session session(){
        try{
        Connection connection =  connectionFactory().createConnection();
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


}
