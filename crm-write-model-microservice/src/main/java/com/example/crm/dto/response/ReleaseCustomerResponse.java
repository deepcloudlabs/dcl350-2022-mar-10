package com.example.crm.dto.response;

public class ReleaseCustomerResponse {
	private final String status;

	public ReleaseCustomerResponse(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
