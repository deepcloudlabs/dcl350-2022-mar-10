package com.example.crm.event;

public class CustomerReleasedEvent extends CustomerEvent {

	public CustomerReleasedEvent(String conversationId, int sequenceId, String customerId) {
		super(conversationId, sequenceId, customerId);
	}

}
