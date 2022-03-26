package com.example.crm.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.crm.event.CustomerEvent;

public interface CustomerEventRepository extends ReactiveMongoRepository<CustomerEvent, String>{

}
