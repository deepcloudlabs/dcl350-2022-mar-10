package com.example.crm.command;

public abstract class Command {
	private String conversationId;

	public Command() {
	}

	public Command(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

}
