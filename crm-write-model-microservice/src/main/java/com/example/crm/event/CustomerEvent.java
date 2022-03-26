package com.example.crm.event;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer_events")
public abstract class CustomerEvent {
	@Id
	private String eventId = UUID.randomUUID().toString();
	private String conversationId;
	private int sequenceId;
	private String customerId;
	private Object eventData;
	private CustomerEventType eventType;
	
	public CustomerEvent() {
	}

	public CustomerEvent(String conversationId, int sequenceId, String customerId) {
		this.conversationId = conversationId;
		this.sequenceId = sequenceId;
		this.customerId = customerId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public int getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Object getEventData() {
		return eventData;
	}

	public void setEventData(Object eventData) {
		this.eventData = eventData;
	}

	public CustomerEventType getEventType() {
		return eventType;
	}

	public void setEventType(CustomerEventType eventType) {
		this.eventType = eventType;
	}

}
