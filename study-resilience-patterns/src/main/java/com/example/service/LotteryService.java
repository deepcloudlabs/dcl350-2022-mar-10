package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class LotteryService implements LotteryClientService {
	private static final String URL = "http://localhost:6100/numbers?column=%d";

	@Override
	@RateLimiter(name ="lottery",fallbackMethod = "fallbackGetir")
	@Retry(name="lottery")
	@CircuitBreaker(name = "lottery")
	@Bulkhead(name="lottery",type = Type.THREADPOOL)
	public String getNumbers(int column) {
		var rt = new RestTemplate();
		return rt.getForEntity(URL.formatted(column), String.class)
				 .getBody();
	}

	public String fallbackGetir(int column, Throwable t) {
		return """
				[ 
					[4,8,15,16,23,42],
					[1,2,3,4,5,6]
				]
		""";
	}
}
