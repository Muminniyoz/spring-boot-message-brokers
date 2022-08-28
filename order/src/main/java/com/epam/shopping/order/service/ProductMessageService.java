package com.epam.shopping.order.service;

import com.epam.shopping.order.config.JmsConfig;
import com.epam.shopping.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductMessageService {
    Logger logger = LoggerFactory.getLogger(ProductMessageService.class.getName());

    @Autowired
    @Qualifier(JmsConfig.JMS_TEMPLATE_TOPIC_BEAN_NAME)
    private JmsTemplate jmsTemplate;

    @Value("${activemq.topic.product-inform}")
    private String productInformTopic;
    public void sendOrderInfoToProductService(Order order){

        jmsTemplate.convertAndSend("VirtualTopic."+productInformTopic, order);
        logger.info("Message send: " + order.getId());
    }


}
