package com.example.market.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.market.domain.Order;
import com.example.market.domain.Trade;
import com.example.market.repository.OrderRepository;
import com.example.market.repository.TradeRepository;

@RestController
@RequestMapping("orders")
@RequestScope
@CrossOrigin
@Validated
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private TradeRepository tradeRepository;
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@PostMapping
	public Order createOrder(@RequestBody Order order) {
		order.setOrderId(UUID.randomUUID().toString());
		var trade = new Trade();
		trade.setTradeId(UUID.randomUUID().toString());
		trade.setPrice(order.getPrice());
		trade.setQuantity(order.getQuantity());
		trade.setSymbol(order.getSymbol());
		tradeRepository.insert(trade);
		var insertedOrder = orderRepository.insert(order);
		eventPublisher.publishEvent(trade);
		return insertedOrder;
	}
		
}
