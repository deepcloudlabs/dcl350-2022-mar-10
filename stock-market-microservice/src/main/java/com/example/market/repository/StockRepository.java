package com.example.market.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.market.domain.Stock;

public interface StockRepository extends MongoRepository<Stock,String>{

}
