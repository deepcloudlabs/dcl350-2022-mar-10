package com.example.market.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.market.service.WebSocketTradeService;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {
	@Autowired
	private WebSocketTradeService webSocketTradeService;
	
	// ws://localhost:2121/market/api/v1/trades
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketTradeService, "/trades")
		        .setAllowedOriginPatterns("*");
		
	}

}
