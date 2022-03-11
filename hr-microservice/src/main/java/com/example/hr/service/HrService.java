package com.example.hr.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.request.IncreaseSalaryRequest;
import com.example.hr.dto.response.EmployeeResponse;

@Service
public class HrService {
	private HrApplication hrApplication;
	// Object 2 Object Mapping
	private ModelMapper modelMapper;
	
	public HrService(HrApplication hrApplication, ModelMapper modelMapper) {
		this.hrApplication = hrApplication;
		this.modelMapper = modelMapper;
	}

	public EmployeeResponse hireEmployee(HireEmployeeRequest request) {
		var employee = modelMapper.map(request, Employee.class);
		var hiredEmployee = hrApplication.hireEmployee(employee)
				.orElseThrow(() -> new IllegalArgumentException("Already exisiting employee."));
		return modelMapper.map(hiredEmployee, EmployeeResponse.class);
	}

	public EmployeeResponse findEmployeeByIdentity(String identityNo) {
		var employee = hrApplication.getEmployeeInformation(IdentityNo.valueOf(identityNo))				
				.orElseThrow(() -> new IllegalArgumentException("Employee does not exist."));
		return modelMapper.map(employee, EmployeeResponse.class);
	}

	public EmployeeResponse fireEmployee(String identityNo) {
		var firedEmployee = hrApplication.fireEmployee(IdentityNo.valueOf(identityNo))				
				.orElseThrow(() -> new IllegalArgumentException("Employee does not exist."));
		return modelMapper.map(firedEmployee, EmployeeResponse.class);
	}

	public List<EmployeeResponse> updateSalaryInDepartment(IncreaseSalaryRequest request) {
		return hrApplication.increaseSalary(request.getDepartment(), 
				                            request.getRate())
		             .stream()
		             .map( emp -> modelMapper.map(emp, EmployeeResponse.class))
		             .toList();
	}

}
