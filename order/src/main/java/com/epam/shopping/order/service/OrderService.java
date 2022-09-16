package com.epam.shopping.order.service;

import com.epam.shopping.order.model.Order;
import com.epam.shopping.order.model.OrderLine;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

@Service
public class OrderService {
    private final List<Order> database = new ArrayList<>();
    private Long lastOrderId = 0L;
    private Long lastOrderLineId = 0L;

    @Autowired
    OrderMessagingService orderMessagingService;

    public Order create(Order order){
        if(order.getOrderLines() == null) throw new NullPointerException("Order linews should not be null");

        double totalPrice =   order.getOrderLines().stream().peek(l-> {
            l.setId(lastOrderLineId++);
        }).mapToDouble(OrderLine::getPrice).sum();
        order.setId(lastOrderId++);
        order.setTotalPrice((float) totalPrice);
        database.add(order);
        orderMessagingService.sendOrderMessage(order);
        return order;
    }
}
