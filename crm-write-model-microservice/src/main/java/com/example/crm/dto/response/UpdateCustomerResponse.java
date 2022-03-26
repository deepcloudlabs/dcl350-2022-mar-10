package com.example.crm.dto.response;

public class UpdateCustomerResponse {
	private final String status;

	public UpdateCustomerResponse(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
