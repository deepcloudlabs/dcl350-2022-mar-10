package com.example.market.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.market.domain.Stock;
import com.example.market.repository.StockRepository;

@RestController
@RequestMapping("stocks")
@RequestScope
@CrossOrigin
@Validated
public class StockController {
	@Autowired
	private StockRepository stockRepository;
	
	@PostMapping
	public Stock createStock(@RequestBody Stock stock) {
		return stockRepository.insert(stock);
	}
	
	@GetMapping("{symbol}")
	public Stock getStock(@PathVariable 
			@NotBlank String symbol) {
		return stockRepository.findById(symbol).orElseThrow( () -> new IllegalArgumentException("Cannot find the stock"));
	}
	
}
