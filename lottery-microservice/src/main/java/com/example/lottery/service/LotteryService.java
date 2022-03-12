package com.example.lottery.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class LotteryService {
	private int lotteryMax;
	private int lotterySize;
	
	public LotteryService(
			@Value("${lottery.max}") int lotteryMax, 
			@Value("${lottery.size}") int lotterySize) {
		this.lotteryMax = lotteryMax;
		this.lotterySize = lotterySize;
	}

	public List<List<Integer>> draw(int column) {
		return IntStream.range(0, column)
				        .mapToObj(i -> draw())
				        .toList();
	}
	
	public List<Integer> draw(){
		return ThreadLocalRandom.current()
				.ints(1, lotteryMax).distinct().limit(lotterySize).sorted().boxed().toList();
	}
}
