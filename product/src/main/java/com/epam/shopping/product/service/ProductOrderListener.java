package com.epam.shopping.product.service;

import com.epam.shopping.product.config.JmsConfig;
import com.epam.shopping.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class ProductOrderListener {

    Logger logger = LoggerFactory.getLogger(ProductOrderListener.class);
    @Autowired
    Session session;

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "${activemq.queue.product-inform}", containerFactory = JmsConfig.JMS_LISTENER_BEAN_NAME)
    public void receiveOrder(Message message){
        try {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setJMSCorrelationID(message.getJMSCorrelationID());
            if(message instanceof ObjectMessage){
                ObjectMessage objm = (ObjectMessage) message;
                Order order = (Order) objm.getObject();
                textMessage.setText("Reply: Order was deleverage successfully: OrderId: "+ order.getId());
                System.out.println("Order: " + order.getId());
            } else {
                textMessage.setText("Reply: Other message come" + message);
            }
            jmsTemplate.convertAndSend(message.getJMSReplyTo(), textMessage);


        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
    }
}
