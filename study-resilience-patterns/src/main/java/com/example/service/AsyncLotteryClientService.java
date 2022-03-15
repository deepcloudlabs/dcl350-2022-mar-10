package com.example.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;

@SuppressWarnings("deprecation")
@Service
public class AsyncLotteryClientService {
	private static final String URL = "http://localhost:6100/numbers?column=%d";
	@Scheduled(fixedRate = 100)
	public void draw(){
		var restTemplate = new AsyncRestTemplate();
		restTemplate.getForEntity(URL.formatted(3), String.class)
		            .addCallback(System.out::println, System.err::println);
	}
}
