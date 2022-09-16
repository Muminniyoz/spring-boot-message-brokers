# spring-boot-message-brokers - rabbitmq - 1

1. GATEWAY 
    This app send order message to order microservice repeatly in second
    
2. ORDER (PUBLISHER)
    This app receive order message and save it database and send message to message broker

3. NOTIFICATION (SUBSCRIBER)
    This app receive message broker message. If message retried three times it sent message to failed queue

4. FAILED-MESSAGE-RESOLVER (SUBSCRIBER)
    This app receive failed message
