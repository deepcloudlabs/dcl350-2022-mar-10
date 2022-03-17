package com.example.market.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebSocketTradeService implements WebSocketHandler {
	private Map<String,WebSocketSession> sessions = 
			new ConcurrentHashMap<>();
	
	@KafkaListener(topics = "stock-trades")
	public void listenTradeEvent(String trade) {
		sessions.values()
		        .parallelStream()
		        .forEach( session -> {
		        	try {
		        		var wsm = new TextMessage(trade);
		        		session.sendMessage(wsm);		        		
		        	}catch (Exception e) {
		        		System.err.println("Error while convertion event to json: "+e.getMessage());
					}
		        });
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// no message is expected!
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		System.err.println("Transportation error: %s at %s".formatted(e.getMessage(),session.getId()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		sessions.remove(session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
