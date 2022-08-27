package com.epam.shopping.order.resource.web;

import com.epam.shopping.order.model.Order;
import com.epam.shopping.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping()
    public ResponseEntity<Order> create(@RequestBody Order order){
        return ResponseEntity.ok( orderService.create(order));
    }
}
