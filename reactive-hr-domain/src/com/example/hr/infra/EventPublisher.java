package com.example.hr.infra;

import java.util.concurrent.CompletableFuture;

import com.example.hr.application.business.event.EmployeeEvent;

public interface EventPublisher {

	CompletableFuture<Void> publish(EmployeeEvent employeeEvent);

}
