package com.example.crm.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.dto.request.CustomerRequest;
import com.example.crm.dto.response.CustomerResponse;
import com.example.crm.service.CrmReactiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
@CrossOrigin
@Validated
public class CrmReactiveRestController {
	private CrmReactiveService crmService;
	
	public CrmReactiveRestController(CrmReactiveService crmService) {
		this.crmService = crmService;
	}

	@GetMapping("{identity}")
	public Mono<CustomerResponse> getCustomerInformation(@PathVariable String identity) {
		return crmService.findCustomerByIdentity(identity);
	}
	
	@GetMapping
	public Flux<CustomerResponse> getCustomers(
		@RequestParam int pageNo,	
		@RequestParam int pageSize	
			) {
		return crmService.findCustomers(pageNo,pageSize);
	}
	
	@PostMapping
	public Mono<CustomerResponse> acquireCustomer(@RequestBody CustomerRequest request) {
		return crmService.addCustomer(request);
	}
	
	@DeleteMapping("{identity}")
	public Mono<CustomerResponse> releaseCustomer(@PathVariable String identity) {
		return crmService.removeCustomer(identity);
	}
	
	
}
