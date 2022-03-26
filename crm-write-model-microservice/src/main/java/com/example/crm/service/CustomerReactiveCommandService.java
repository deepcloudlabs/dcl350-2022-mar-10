package com.example.crm.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.command.AcquireCustomerCommand;
import com.example.crm.command.UpdateCustomerCommand;
import com.example.crm.dto.response.AcquireCustomerResponse;
import com.example.crm.dto.response.ReleaseCustomerResponse;
import com.example.crm.dto.response.UpdateCustomerResponse;
import com.example.crm.event.CustomerAcquiredEvent;
import com.example.crm.event.CustomerReleasedEvent;
import com.example.crm.event.CustomerUpdatedEvent;
import com.example.crm.repository.CustomerEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class CustomerReactiveCommandService {
	private final CustomerEventRepository customerEventRepository;
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;

	public CustomerReactiveCommandService(CustomerEventRepository customerEventRepository,
			RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.customerEventRepository = customerEventRepository;
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}

	public Mono<AcquireCustomerResponse> createCustomer(AcquireCustomerCommand command) throws JsonProcessingException {
		var customerAcquiredEvent = new CustomerAcquiredEvent(command.getConversationId(),1,command.getIdentity());
		customerAcquiredEvent.setEventData(command);
		customerEventRepository.save(customerAcquiredEvent);
		var eventAsJson = objectMapper.writeValueAsString(customerAcquiredEvent);
		rabbitTemplate.convertAndSend("custeventsexc", null, eventAsJson);
		return Mono.just(new AcquireCustomerResponse("ok")); 
	}

	public Mono<UpdateCustomerResponse> updateCustomer(UpdateCustomerCommand command) throws JsonProcessingException {
		var customerUpdatedEvent = new CustomerUpdatedEvent(command.getConversationId(),1,command.getIdentity());
		customerEventRepository.save(customerUpdatedEvent);
		customerUpdatedEvent.setEventData(command);
		var eventAsJson = objectMapper.writeValueAsString(customerUpdatedEvent);
		rabbitTemplate.convertAndSend("custeventsexc", null, eventAsJson);
		return Mono.just(new UpdateCustomerResponse("ok")); 
	}

	public Mono<ReleaseCustomerResponse> removeCustomer(String identity,String conversationId) throws JsonProcessingException {
		var customerReleasedEvent = new CustomerReleasedEvent(conversationId,1,identity);
		customerEventRepository.save(customerReleasedEvent);
		var eventAsJson = objectMapper.writeValueAsString(customerReleasedEvent);
		rabbitTemplate.convertAndSend("custeventsexc", null, eventAsJson);
		return Mono.just(new ReleaseCustomerResponse("ok")); 
	}

}
