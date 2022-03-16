package com.example.crm.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.crm.events.CustomerEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventRabbitAdapter {
	@Autowired
	private RabbitTemplate rt;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${exchange}")
	private String exchangeName;
	
	@EventListener
	public void listenCustomerEvent(CustomerEvent event) throws JsonProcessingException {
		var eventAsJson = objectMapper.writeValueAsString(event);
		rt.convertAndSend(exchangeName, null,eventAsJson );
	}
	
	
}
