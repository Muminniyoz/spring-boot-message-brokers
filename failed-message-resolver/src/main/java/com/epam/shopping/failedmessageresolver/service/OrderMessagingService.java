package com.epam.shopping.failedmessageresolver.service;

import com.epam.shopping.failedmessageresolver.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrderMessagingService {

    Random random = new Random();


    @RabbitListener(queues = "${app.rabbitmq.order.queue}.failed")
    public void getOrderMessage(@Payload Order order, Message message){
        log.info("order: "+order.getId());

    }

}
