package com.example.crm.event;

public class CustomerUpdatedEvent extends CustomerEvent {

	public CustomerUpdatedEvent(String conversationId, int sequenceId, String customerId) {
		super(conversationId, sequenceId, customerId);
	}

}
