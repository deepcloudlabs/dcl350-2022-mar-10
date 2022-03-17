package com.example.market.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.market.domain.Trade;

public interface TradeRepository extends MongoRepository<Trade,String>{

}
