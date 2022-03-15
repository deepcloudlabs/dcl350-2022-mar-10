package com.example.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableDiscoveryClient
public class LotteryConsumerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteryConsumerServiceApplication.class, args);
	}

}
