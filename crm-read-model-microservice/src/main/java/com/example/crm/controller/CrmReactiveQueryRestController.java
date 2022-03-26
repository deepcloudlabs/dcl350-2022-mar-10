package com.example.crm.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.document.CustomerDocument;
import com.example.crm.service.CustomerReactiveQueryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
@CrossOrigin
@Validated
public class CrmReactiveQueryRestController {
    private final CustomerReactiveQueryService customerService;
	
	public CrmReactiveQueryRestController(CustomerReactiveQueryService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("{identity}")
	public Mono<CustomerDocument> getCustomerByIdentity(@PathVariable @NotBlank String identity) {
		return customerService.findCustomerById(identity);
	}
	
	@GetMapping
	public Flux<CustomerDocument> getCustomersByPage(
			@RequestParam @Min(0) int pageNo,
			@RequestParam @Max(50) int pageSize) {
		return customerService.findCustomers(pageNo,pageSize);
	}
	
}
