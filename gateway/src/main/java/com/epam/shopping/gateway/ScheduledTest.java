package com.epam.shopping.gateway;

import com.epam.shopping.gateway.model.Order;
import com.epam.shopping.gateway.model.OrderLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class ScheduledTest {
    Logger logger = LoggerFactory.getLogger(ScheduledTest.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedDelay = 3000)
    public void sendOrder(){
        List<OrderLine> orderLines = new ArrayList<>();
        Order order = new Order();
        orderLines.add(new OrderLine(1L, 1L, 1F, 10000F, null));
        orderLines.add(new OrderLine(1L, 2L, 1.1F, 12000F, null));
        orderLines.add(new OrderLine(1L, 3L, 1.2F, 16000F, null));
        orderLines.add(new OrderLine(1L, 4L, 1.3F, 18000F, null));
        orderLines.add(new OrderLine(1L, 5L, 1.4F, 12000F, null));
        order.setOrderLines(orderLines);
        try{
            Order o = restTemplate.postForEntity("http://shopping-product-order/api/order", order, Order.class).getBody();
            assert o != null;
            logger.info("Succesfuly: "+o.getId());
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }

    }


}
