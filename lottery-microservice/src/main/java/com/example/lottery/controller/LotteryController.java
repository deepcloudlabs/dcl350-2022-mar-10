package com.example.lottery.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.lottery.service.LotteryService;

@RestController
@RequestMapping("numbers")
@RequestScope
@Validated
public class LotteryController {
	@Value("${server.port}")
	private int port;
	
	private LotteryService lotteryService;

	public LotteryController(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	@GetMapping(params= {"column"})
	public List<List<Integer>> getLotteryNumbers(
			@RequestParam(required = false, defaultValue = "10") 
			@Min(3) int column){
		System.err.println("LotteryController::getLotteryNumbers() @ "+port);
		return lotteryService.draw(column);
	}
}
