package com.example.library.domain;

public class OduncKitapAlmaKarari {
	private final boolean decision;
	private final String message;
	private final Integer duration;

	public OduncKitapAlmaKarari(boolean decision, String message, Integer duration) {
		this.decision = decision;
		this.message = message;
		this.duration = duration;
	}

	public boolean isDecision() {
		return decision;
	}

	public String getMessage() {
		return message;
	}

	public Integer getDuration() {
		return duration;
	}

}
