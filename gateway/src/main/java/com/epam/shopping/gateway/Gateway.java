package com.epam.shopping.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableEurekaServer
@EnableEurekaClient
public class Gateway
{
    public static void main( String[] args )
    {
        SpringApplication.run(Gateway.class, args);
    }

    @Bean()
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate rest = new RestTemplate();
        rest.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
        return rest;
    }
}
