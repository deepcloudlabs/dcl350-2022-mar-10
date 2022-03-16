package com.example.crm.events;

import com.example.crm.document.CustomerDocument;

public class CustomerAcquiredEvent extends CustomerEvent {

	public CustomerAcquiredEvent(String identity, String email) {
		super(identity, email);
	}

	public CustomerAcquiredEvent(CustomerDocument customer) {
		this(customer.getIdentity(), customer.getEmail());
	}

}
