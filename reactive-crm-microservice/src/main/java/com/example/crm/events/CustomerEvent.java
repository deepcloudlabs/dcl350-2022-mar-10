package com.example.crm.events;

import java.util.UUID;

public class CustomerEvent {
	private final String eventId;
	private final String identity;
	private final String email;

	public CustomerEvent(String identity, String email) {
		this.eventId = UUID.randomUUID().toString();
		this.identity = identity;
		this.email = email;
	}

	public String getEventId() {
		return eventId;
	}

	public String getIdentity() {
		return identity;
	}

	public String getEmail() {
		return email;
	}

}
