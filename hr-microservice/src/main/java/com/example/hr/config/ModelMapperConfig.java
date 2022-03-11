package com.example.hr.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.domain.Employee;
import com.example.hr.domain.FullName;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;

@Configuration
public class ModelMapperConfig {
	private static final Converter<HireEmployeeRequest,Employee>
	HIRE_EMPLOYEE_REQUEST_TO_EMPLOYEE_CONVERTER = (context) -> {
		var request = context.getSource();
		return new Employee.Builder(request.getIdentity())
				        .fullname(request.getFirstName(), request.getLastName())
				        .iban(request.getIban())
				        .salary(request.getSalary())
				        .jobStyle(request.getJobStyle())
				        .birthYear(request.getBirthYear())
				        .photo(request.getPhoto())
				        .department(request.getDepartment())
				        .build();
	};
	
	private static final Converter<Employee,EmployeeResponse>
	EMPLOYEE_TO_EMPLOYEE_RESPONSE_CONVERTER = (context) -> {
		var employee = context.getSource();
		var fullname = employee.getFullname();
		var response = new EmployeeResponse();
		response.setIdentity(employee.getIdentity().getValue());
		response.setFirstName(fullname.getFirstName());
		response.setLastName(fullname.getLastName());
		response.setSalary(employee.getSalary().getValue());
		response.setIban(employee.getIban().value());
		response.setDepartment(employee.getDepartment());
		response.setPhoto(employee.getPhoto().getBase64Value());
		response.setBirthYear(employee.getBirthYear().getValue());
		response.setJobStyle(employee.getJobStyle());
		return response;
	};
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.addConverter(HIRE_EMPLOYEE_REQUEST_TO_EMPLOYEE_CONVERTER, 
				HireEmployeeRequest.class, Employee.class);
		modelMapper.addConverter(EMPLOYEE_TO_EMPLOYEE_RESPONSE_CONVERTER, 
				Employee.class, EmployeeResponse.class);
		return modelMapper;
	}
}
