package com.example.crm.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.crm.document.CustomerDocument;
import com.example.crm.event.CustomerEvent;
import com.example.crm.repository.CustomerDocumentRepository;

@Service
public class CustomerEventListener {
	private final CustomerDocumentRepository customerDocumentRepository;

	public CustomerEventListener(CustomerDocumentRepository customerDocumentRepository) {
		this.customerDocumentRepository = customerDocumentRepository;
	}

	@RabbitListener(queues = "custeventqueue")
	public void listenCustomerEvents(CustomerEvent event) {
		switch (event.getEventType()) {
			case CUSTOMER_ACQUIRED -> {
				customerDocumentRepository.save((CustomerDocument)event.getEventData());
			}
			case CUSTOMER_RELEASED -> {
				customerDocumentRepository.deleteById(event.getCustomerId());
			}
			case CUSTOMER_UPDATED -> {
				
			}
		}
	}
}
