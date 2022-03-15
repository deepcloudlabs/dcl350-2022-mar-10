package com.example.lottery.client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "lottery")
public interface LotteryService {

	@GetMapping("/numbers")
	List<List<Integer>> getir(@RequestParam int column);
}
