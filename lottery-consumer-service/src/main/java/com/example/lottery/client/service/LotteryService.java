package com.example.lottery.client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(value = "lottery")
public interface LotteryService {

	@GetMapping("/numbers")
	@Retry(name="lottery",fallbackMethod = "fallbackGetir")
	List<List<Integer>> getir(@RequestParam int column);
	
	public default List<List<Integer>> fallbackGetir(int column,Throwable t){
		return List.of(
			List.of(4,8,15,16,23,42),	
			List.of(1,2,3,4,5,6)	
		);
	}
}
