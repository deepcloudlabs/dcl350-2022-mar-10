package com.example.crm.service;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.crm.document.CustomerDocument;
import com.example.crm.dto.request.CustomerRequest;
import com.example.crm.dto.response.CustomerResponse;
import com.example.crm.events.CustomerAcquiredEvent;
import com.example.crm.events.CustomerReleasedEvent;
import com.example.crm.repository.CustomerReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CrmReactiveService {
	private final Function<CustomerDocument,CustomerResponse> 
	CUSTOMER_DOCUMENT_TO_CUSTOMER_RESPONSE_MAPPER ;
	
	private final CustomerReactiveRepository customerRepository;
	private final ModelMapper modelMapper;
	private final ApplicationEventPublisher publisher;
	
	public CrmReactiveService(CustomerReactiveRepository customerRepository, ModelMapper modelMapper,ApplicationEventPublisher publisher) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
		this.publisher = publisher;
		CUSTOMER_DOCUMENT_TO_CUSTOMER_RESPONSE_MAPPER = doc -> modelMapper.map(doc, CustomerResponse.class);
	}

	public Mono<CustomerResponse> findCustomerByIdentity(String identity) {
		return customerRepository.findById(identity)
				                 .map(CUSTOMER_DOCUMENT_TO_CUSTOMER_RESPONSE_MAPPER);
	}

	public Flux<CustomerResponse> findCustomers(int pageNo, int pageSize) {
		return customerRepository.findAll(PageRequest.of(pageNo, pageSize))
				                 .map(CUSTOMER_DOCUMENT_TO_CUSTOMER_RESPONSE_MAPPER);
	}

	public Mono<CustomerResponse> addCustomer(CustomerRequest request) {
		var customerDocument = modelMapper.map(request, CustomerDocument.class);
		return customerRepository.insert(customerDocument)
				                 .doOnNext(custDoc -> publisher.publishEvent(new CustomerAcquiredEvent(custDoc)))
				                 .map(CUSTOMER_DOCUMENT_TO_CUSTOMER_RESPONSE_MAPPER);
	}

	public Mono<CustomerResponse> removeCustomer(String identity) {
		return customerRepository.findById(identity)
		                  .doOnNext(cust -> customerRepository.delete(cust).subscribe(System.out::println))
		                  .doOnNext(custDoc -> publisher.publishEvent(new CustomerReleasedEvent(custDoc)))
		                  .map(CUSTOMER_DOCUMENT_TO_CUSTOMER_RESPONSE_MAPPER);
	}

}
