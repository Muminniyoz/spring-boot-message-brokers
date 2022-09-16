package com.epam.shopping.order.service;

import com.epam.shopping.order.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderMessagingService {
    @Value("${app.rabbitmq.order.exchange}")
    private String orderExchange;
    @Value("${app.rabbitmq.order.routingKey}")
    private String orderRoutingKey;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(Order order){
        rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, order);
    }

}
