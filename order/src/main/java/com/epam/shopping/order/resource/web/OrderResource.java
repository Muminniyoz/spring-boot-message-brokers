package com.epam.shopping.order.resource.web;

import com.epam.shopping.order.model.Order;
import com.epam.shopping.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping()
    public ResponseEntity<Order> create(@RequestBody Order order){
        order = orderService.create(order);
        log.info("Order: " + order.getId());
        return ResponseEntity.ok(order);
    }
}
