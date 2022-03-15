package com.example.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

public interface LotteryClientService {

	@RateLimiter(name = "lottery", fallbackMethod = "fallbackGetir")
	String getNumbers(int column);

	public default String fallbackGetir(int column, Throwable t) {
		return """
						[
							[4,8,15,16,23,42],
							[1,2,3,4,5,6]
						]
				""";
	}
}