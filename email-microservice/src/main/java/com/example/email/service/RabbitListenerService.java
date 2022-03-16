package com.example.email.service;

import javax.mail.internet.MimeMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitListenerService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private ObjectMapper objectMapper;

	@RabbitListener(queues = "${customerQueue}")
	public void listenCustomerEvent(String message) throws JsonMappingException, JsonProcessingException {
		var customerEvent = objectMapper.readValue(message, CustomerEvent.class);
		var emailMessage = new SimpleMailMessage();
		emailMessage.setFrom("noreply@sahabt.com");
		emailMessage.setTo(customerEvent.getEmail());
		emailMessage.setSubject("About crm");
		emailMessage.setText("Hello World!");
		javaMailSender.send(emailMessage);
	}
}

class CustomerEvent {
	private String identity;
	private String email;

	public CustomerEvent() {
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CustomerEvent [identity=" + identity + ", email=" + email + "]";
	}

}