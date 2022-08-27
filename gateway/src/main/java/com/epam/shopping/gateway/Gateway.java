package com.epam.shopping.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class Gateway
{
    public static void main( String[] args )
    {
        SpringApplication.run(Gateway.class, args);
    }

    @Bean()
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
