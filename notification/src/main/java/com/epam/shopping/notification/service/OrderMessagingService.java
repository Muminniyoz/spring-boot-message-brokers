package com.epam.shopping.notification.service;

import com.epam.shopping.notification.model.Order;
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
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.order.routingKey}")
    private String orderRoutingKey;
    @Value("${app.rabbitmq.order.exchange}")
    private String orderExchange;

    @RabbitListener(queues = "${app.rabbitmq.order.queue}")
    public void getOrderMessage(@Payload Order order, Message message){

        if(hasExceededRetryCount(message)){
            putIntoParkingLot(message);
            return;
        }
        if(random.nextInt(1000)%2==0){
            // failed
            log.info("order failed: " + order.getId());



            throw new RuntimeException("order failed: " + order.getId());
        } else {
            // success
            log.info("order successfully: " + order.getId());
        }
    }

    private boolean hasExceededRetryCount(Message in) {
        List<Map<String, ?>> xDeathHeader = in.getMessageProperties().getXDeathHeader();
        log.info("xDeathHeader: " + xDeathHeader);
        if (xDeathHeader != null && xDeathHeader.size() >= 1) {
            Long count = (Long) xDeathHeader.get(0).get("count");
            return count >= 3;
        }
        return false;
    }

    private void putIntoParkingLot(Message failedMessage) {
        log.info("Retries exeeded putting into parking lot");
       this.rabbitTemplate.convertAndSend(orderExchange + ".failed",orderRoutingKey + ".failed", failedMessage);
    }


}
