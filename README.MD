
<h1>Homework #ACITVEMQ-1</h1>

1. Run gateway app <br>
   gateway application sent order to order app in every 3s
2. run order app <br>
   order app receive order details and send to activemq message to product to reduce product
3. run product app <br>
   product app reciee order details and reduce product in database
<br>
<a href="product/src/main/java/com/epam/shopping/product/config/JmsConfig.java">JmsConfig.java</a> file contains durable and un-durable subscription configurations
