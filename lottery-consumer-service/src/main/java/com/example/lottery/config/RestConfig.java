package com.example.lottery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
	@Bean
	public RestTemplate restTemplate() {
		var rt = new RestTemplate();

		return rt;
	}
}
