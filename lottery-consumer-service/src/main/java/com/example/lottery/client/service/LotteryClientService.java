package com.example.lottery.client.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnProperty(value="loadBalancing", havingValue = "manual")
public class LotteryClientService {
	private static final String REST_API_URL = "http://%s:%d/numbers?column=10";
	private RestTemplate restTemplate;
	private DiscoveryClient discoveryClient;
	private AtomicInteger counter = new AtomicInteger();
	private List<ServiceInstance> instances;
	
	public LotteryClientService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	@PostConstruct
	public void init() {
		instances = discoveryClient.getInstances("lottery");
	}
	
    // URL : 
	
	// I) Server-side load balancing -> dns/ip:port 
	//   i) hardware: netscaler, f5 
	//  ii) software: httpd, nginx,haproxy 
	// iii) api gateway -> Zuul -- reactive --> Spring Cloud Gateway

	// Reactive Programming, Reactive System, Reactive MicroService
	
	// II) Client-side load balancing -> Consumer -> List of Instances -> Registry Server
	//     Registry Server: Service Provider -> Lottery MicroService 
	//     Spring Cloud -> Eureka Server -> Netflix OSS
	@Scheduled(fixedRate = 3_000)
	public void consumeLotteryService() {
		var response = restTemplate.getForEntity(getNextUrl(), String.class);
		System.out.println(response.getBody());
	}
	
	private String getNextUrl() {
		var index = counter.getAndIncrement() % instances.size();
		var instance = instances.get(index);
		String host = instance .getHost();
		int port = instance.getPort();
		return REST_API_URL.formatted(host ,port);
	}
}
