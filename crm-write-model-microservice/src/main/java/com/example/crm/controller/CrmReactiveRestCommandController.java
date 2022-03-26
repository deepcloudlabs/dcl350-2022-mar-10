package com.example.crm.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.command.AcquireCustomerCommand;
import com.example.crm.command.UpdateCustomerCommand;
import com.example.crm.dto.response.AcquireCustomerResponse;
import com.example.crm.dto.response.ReleaseCustomerResponse;
import com.example.crm.dto.response.UpdateCustomerResponse;
import com.example.crm.service.CustomerReactiveCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
@CrossOrigin
@Validated
public class CrmReactiveRestCommandController {
    private final CustomerReactiveCommandService customerService;
	
	public CrmReactiveRestCommandController(CustomerReactiveCommandService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping
	public Mono<AcquireCustomerResponse> addCustomer(@RequestBody AcquireCustomerCommand command) throws JsonProcessingException{
		return customerService.createCustomer(command);
	}
	
	@PutMapping
	public Mono<UpdateCustomerResponse> updateCustomer(@RequestBody UpdateCustomerCommand command) throws JsonProcessingException{
		return customerService.updateCustomer(command);
	}
	
	@DeleteMapping("{identity}")
	public Mono<ReleaseCustomerResponse> deleteCustomer(@PathVariable String identity,@RequestParam String conversationId) throws JsonProcessingException{
		return customerService.removeCustomer(identity,conversationId);
	}
}
