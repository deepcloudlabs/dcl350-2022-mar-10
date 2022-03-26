package com.example.hr.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.request.IncreaseSalaryRequest;
import com.example.hr.dto.response.EmployeeResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HrReactiveService {
	private HrApplication hrApplication;
	private ModelMapper modelMapper;
	
	public HrReactiveService(HrApplication hrApplication, ModelMapper modelMapper) {
		this.hrApplication = hrApplication;
		this.modelMapper = modelMapper;
	}

	public Mono<EmployeeResponse> hireEmployee(HireEmployeeRequest request) {
		var employee = modelMapper.map(request, Employee.class);
		return Mono.fromFuture( hrApplication.hireEmployee(employee))
				.map( hiredEmployee -> {
					if (hiredEmployee.isEmpty())
						throw new IllegalArgumentException("Already exisiting employee.");
					return modelMapper.map(hiredEmployee.get(), EmployeeResponse.class);
				});
	}

	public Mono<EmployeeResponse> findEmployeeByIdentity(String identityNo) {
		return Mono.fromFuture(hrApplication.getEmployeeInformation(IdentityNo.valueOf(identityNo)))
				.map( emp -> {
					if (emp.isEmpty())
						throw new IllegalArgumentException("Employee does not exist.");
					return modelMapper.map(emp.get(), EmployeeResponse.class);
				});				
	}

	public Mono<EmployeeResponse> fireEmployee(String identityNo) {
		return Mono.fromFuture( hrApplication.fireEmployee(IdentityNo.valueOf(identityNo)))				
				.map(firedEmployee -> {
					if (firedEmployee.isEmpty())
					  throw new IllegalArgumentException("Employee does not exist.");
					return modelMapper.map(firedEmployee.get(), EmployeeResponse.class);
				});
	}

	public Flux<EmployeeResponse> updateSalaryInDepartment(IncreaseSalaryRequest request) {
		return Mono.fromFuture(hrApplication.increaseSalary(request.getDepartment(),request.getRate()))
				   .flatMapMany(Flux::fromIterable)
		           .map( emp -> modelMapper.map(emp, EmployeeResponse.class));
	}

}
