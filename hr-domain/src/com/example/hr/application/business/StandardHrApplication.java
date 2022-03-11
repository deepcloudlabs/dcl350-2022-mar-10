package com.example.hr.application.business;

import java.util.List;
import java.util.Optional;

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
	public Optional<Employee> hireEmployee(Employee employee) {
		var identity = employee.getIdentity();
		if(employeeRepository.exists(identity))
			return Optional.empty();
		eventPublisher.publish(new EmployeeHiredEvent(identity));
		return Optional.of(employeeRepository.createEmployee(employee));
	}

	@Override
	public Optional<Employee> fireEmployee(IdentityNo id) {
		Optional<Employee> firedEmployee = 
				employeeRepository.removeEmployee(id);
		firedEmployee.ifPresent( 
			emp -> eventPublisher.publish(new EmployeeFiredEvent(emp))
		);
		return firedEmployee;
	}

	@Override
	public Optional<Employee> getEmployeeInformation(IdentityNo id) {
		return employeeRepository.findEmployeeByIdentityNo(id);
	}

	@Override
	public List<Employee> increaseSalary(Department department, double rate) {
		var employees = employeeRepository.findAllEmployeesByDepartment(department);
		employees.forEach( employee -> {
			employee.setSalary(employee.getSalary().multiply(1.0 + rate));
			eventPublisher.publish(new SalaryChangedEvent(employee));
		});		
		return employees;
	}

}
