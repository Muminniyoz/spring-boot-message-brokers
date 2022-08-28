package com.epam.shopping.product.service;

import com.epam.shopping.product.config.JmsConfig;
import com.epam.shopping.order.model.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductOrderListener {

    @JmsListener(destination = "Consumer.consumer1.VirtualTopic.${activemq.topic.product-inform}", containerFactory = JmsConfig.JMS_LISTENER_BEAN_NAME)
    public void receiveOrder(@Payload Order order){
        System.out.println("Order: "+order.getId());
    }
}
