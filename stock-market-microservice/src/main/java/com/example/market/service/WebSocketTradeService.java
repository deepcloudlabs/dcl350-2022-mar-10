package com.example.market.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.market.domain.Trade;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WebSocketTradeService implements WebSocketHandler {
	private Map<String,WebSocketSession> sessions = 
			new ConcurrentHashMap<>();
	@Autowired
	private ObjectMapper mapper;
	
	@EventListener
	public void listenTradeEvent(Trade event) {
		sessions.values()
		        .parallelStream()
		        .forEach( session -> {
		        	try {
		        		var tradeAsJson =
		        				mapper.writeValueAsString(event);
		        		var wsm = new TextMessage(tradeAsJson);
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
