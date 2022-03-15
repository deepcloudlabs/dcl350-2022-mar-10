package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.service.LotteryClientService;

@RestController
@RequestMapping("exercise")
@RequestScope
public class ExerciseController {

	private LotteryClientService lotteryService;

	public ExerciseController(LotteryClientService lotteryService) {
		this.lotteryService = lotteryService;
		System.err.println(lotteryService.getClass());
	}

	@GetMapping
	public String draw() {
		return lotteryService.getNumbers(10);
	}
}
