package com.example.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuditingFilter implements GlobalFilter {

	// asynchronous
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		var request = exchange.getRequest();
		System.err.println("Path		: " +request.getPath());
		System.err.println("Query Params: " +request.getQueryParams());
		request.getBody().subscribe(System.err::println);
		request.getHeaders().forEach(
			(name,value) -> System.err.println(name+": \t"+value)
		);
		return chain.filter(exchange);
	}

}
