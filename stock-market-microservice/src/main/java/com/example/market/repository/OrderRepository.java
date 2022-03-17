package com.example.market.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.market.domain.Order;

public interface OrderRepository extends MongoRepository<Order,String>{

}
