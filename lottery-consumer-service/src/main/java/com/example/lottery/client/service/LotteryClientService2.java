package com.example.lottery.client.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value="loadBalancing", havingValue = "feign")
public class LotteryClientService2 {
	private LotteryService lotteryService;
	
	public LotteryClientService2(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
		System.err.println(lotteryService.getClass());
	}

	@Scheduled(fixedRate = 3_000)
	public void consumeLotteryService() {
		System.out.println(lotteryService.getir(10));
	}
	
}
