package com.epam.shopping.order.service;

import com.epam.shopping.order.config.JmsConfig;
import com.epam.shopping.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductMessageService {

    @Autowired
    @Qualifier(JmsConfig.JMS_TEMPLATE_TOPIC_BEAN_NAME)
    private JmsTemplate jmsTemplate;

    @Value("${activemq.topic.product-inform}")
    private String productInformTopic;

    public void sendOrderInfoToProductService(Order order){
        jmsTemplate.convertAndSend(productInformTopic, order);
    }


}
