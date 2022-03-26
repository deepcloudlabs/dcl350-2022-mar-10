package com.example.crm.dto.response;

public class AcquireCustomerResponse {
	private final String status;

	public AcquireCustomerResponse(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
