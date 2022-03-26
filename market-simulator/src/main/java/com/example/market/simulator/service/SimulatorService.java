package com.example.market.simulator.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SimulatorService {

	@Scheduled(fixedRate = 1000)
	public void sendRandomOrder() {
		var rt = new RestTemplate();
		var request = new OrderRequest();
		request.setPrice(ThreadLocalRandom.current().nextDouble(100, 200));
		request.setQuantity(ThreadLocalRandom.current().nextDouble(100, 200));
		request.setSymbol("msft");
		request.setSide("ASK");
		
		var response = rt.postForEntity("http://localhost:2100/market/api/v1/orders", 
				request, String.class);
		System.err.println(response.getBody());
	}
}

class OrderRequest {
	private String orderId;
	private String symbol;
	private double price;
	private double quantity;
	private String side;
	public OrderRequest() {
	}
	
	public OrderRequest(String symbol, double price, double quantity, String side) {
		this.symbol = symbol;
		this.price = price;
		this.quantity = quantity;
		this.side = side;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}	
	
}