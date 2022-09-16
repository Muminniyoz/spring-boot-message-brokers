package com.epam.shopping.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NotificationMicroApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(NotificationMicroApplication.class, args);
    }
}
