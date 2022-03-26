package com.example.hr.application.business;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.hr.application.HrApplication;
import com.example.hr.application.business.event.EmployeeFiredEvent;
import com.example.hr.application.business.event.EmployeeHiredEvent;
import com.example.hr.application.business.event.SalaryChangedEvent;
import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;
import com.example.hr.infra.EventPublisher;
import com.example.hr.repository.EmployeeRepository;

public class StandardHrApplication implements HrApplication {
	private EmployeeRepository employeeRepository;
	private EventPublisher eventPublisher;
	
	public StandardHrApplication(EmployeeRepository employeeRepository, EventPublisher eventPublisher) {
		this.employeeRepository = employeeRepository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public CompletableFuture<Optional<Employee>> hireEmployee(Employee employee) {
			var identity = employee.getIdentity();
			return employeeRepository.exists(employee.getIdentity())
					                 .thenApplyAsync(exists -> {
					                	 System.err.println(exists);
													if (exists) {
														return Optional.empty();
													} else {
														eventPublisher.publish(new EmployeeHiredEvent(identity)).thenRun(() -> System.err.println("Event is sent."));
														try {
															return employeeRepository.createEmployee(employee).get();
														} catch (InterruptedException | ExecutionException e) {
															System.err.println("Error: "+e.getMessage());
															return Optional.empty();
														}								
													}
					                 			} 
					                );
	}

	@Override
	public CompletableFuture<Optional<Employee>> fireEmployee(IdentityNo id) {
		return employeeRepository.removeEmployee(id).thenApplyAsync( firedEmployee -> {
				if (firedEmployee.isPresent())
				   eventPublisher.publish(new EmployeeFiredEvent(firedEmployee.get()));
				return firedEmployee;
			});			
	}

	@Override
	public CompletableFuture<Optional<Employee>> getEmployeeInformation(IdentityNo id) {
		return employeeRepository.findEmployeeByIdentityNo(id);
	}

	@Override
	public CompletableFuture<List<Employee>> increaseSalary(Department department, double rate) {
		return employeeRepository.findAllEmployeesByDepartment(department)
				                 .thenApplyAsync( emps -> {
									emps.forEach( employee -> {
										employee.setSalary(employee.getSalary().multiply(1.0 + rate));
										eventPublisher.publish(new SalaryChangedEvent(employee));
									});		
									return emps;
				                 	}
				                 );
	}

}
