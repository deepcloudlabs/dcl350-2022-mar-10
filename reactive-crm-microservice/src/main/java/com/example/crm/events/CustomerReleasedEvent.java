package com.example.crm.events;

import com.example.crm.document.CustomerDocument;

public class CustomerReleasedEvent extends CustomerEvent {

	public CustomerReleasedEvent(String identity, String email) {
		super(identity, email);
	}

	public CustomerReleasedEvent(CustomerDocument customer) {
		this(customer.getIdentity(), customer.getEmail());
	}

}
