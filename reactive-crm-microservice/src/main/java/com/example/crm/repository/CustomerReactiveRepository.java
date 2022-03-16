package com.example.crm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.crm.document.CustomerDocument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerReactiveRepository 
           extends ReactiveMongoRepository<CustomerDocument, String>{
    Mono<CustomerDocument> findByEmail(String email);
    Flux<CustomerDocument> findByBirthYearBetween(int fromYear,int toYear);
    @Query("{}")
	Flux<CustomerDocument> findAll(Pageable page);
}
