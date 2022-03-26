package com.example.crm.event;

public class CustomerAcquiredEvent extends CustomerEvent {

	public CustomerAcquiredEvent(String conversationId, int sequenceId, String customerId) {
		super(conversationId, sequenceId, customerId);
		setEventType(CustomerEventType.CUSTOMER_ACQUIRED);
	}

}
