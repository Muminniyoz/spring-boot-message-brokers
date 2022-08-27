package com.epam.shopping.order.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {
    public static final String JMS_TEMPLATE_TOPIC_BEAN_NAME ="topicJmsTemplate";
    @Bean
    public ActiveMQConnectionFactory connectionFactory(@Value("${spring.activemq.broker-url}") String brokerUrl) {
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    @Bean
    @Qualifier(JMS_TEMPLATE_TOPIC_BEAN_NAME)
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory factory){
        JmsTemplate template = new JmsTemplate(factory);
        template.setPubSubDomain(true);
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


}
