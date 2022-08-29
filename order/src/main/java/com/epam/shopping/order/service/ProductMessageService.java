package com.epam.shopping.order.service;

import com.epam.shopping.order.config.JmsConfig;
import com.epam.shopping.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.UUID;

@Service
public class ProductMessageService {

    Logger logger = LoggerFactory.getLogger(ProductMessageService.class);
    @Autowired
    @Qualifier(JmsConfig.JMS_TEMPLATE_TOPIC_BEAN_NAME)
    private JmsTemplate jmsTemplate;

    @Value("${activemq.queue.product-inform}")
    private String productInformTopic;

    @Autowired
    Session session;

    public void sendOrderInfoToProductService(Order order)  {
        try {

            Destination dest = session.createTemporaryQueue();
            MessageConsumer messageConsumer = session.createConsumer(dest);
            messageConsumer.setMessageListener(reciever());
            ObjectMessage message = session.createObjectMessage();
            message.setObject(order);
            message.setJMSCorrelationID(UUID.randomUUID().toString());
            message.setJMSReplyTo(dest);
            MessageProducer producer= session.createProducer(session.createQueue(productInformTopic));
            producer.send(message);
            logger.info("Message send: "+order.getId());
//            jmsTemplate.convertAndSend(message);
        } catch (JMSException ex){
            logger.error(ex.getMessage(), ex);
        }
    }

    @Bean
    public MessageReceiver reciever(){
        return new MessageReceiver();
    }

    class MessageReceiver implements MessageListener{

        @Override
        public void onMessage(Message message) {
            logger.info("Message come to MessageRecieiver: "+message);
        }
    }


}
