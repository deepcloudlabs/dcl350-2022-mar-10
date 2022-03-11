package com.example.hr.adapter;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;
import com.example.hr.repository.EmployeeDocumentRepository;
import com.example.hr.repository.EmployeeRepository;

@Component
public class EmployeeRepositorySpringDataMongoAdapter implements EmployeeRepository{
	private EmployeeDocumentRepository employeeDocumentRepository;
	private ModelMapper modelMapper;
	
	public EmployeeRepositorySpringDataMongoAdapter(EmployeeDocumentRepository employeeDocumentRepository,
			ModelMapper modelMapper) {
		this.employeeDocumentRepository = employeeDocumentRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean exists(IdentityNo identity) {
		return employeeDocumentRepository.existsById(identity.getValue());
	}

	@Override
	public Employee createEmployee(Employee employee) {
		var document = modelMapper.map(employee, EmployeeDocument.class);
		var newEmployeeDocument = employeeDocumentRepository.save(document);
		return modelMapper.map(newEmployeeDocument, Employee.class);
	}

	@Override
	public Optional<Employee> removeEmployee(IdentityNo id) {
		var employeeDocument = employeeDocumentRepository.findById(id.getValue());
		if (employeeDocument.isEmpty())
			return Optional.empty();
		var document = employeeDocument.get();		
		employeeDocumentRepository.delete(document);
		return Optional.of(modelMapper.map(document, Employee.class));
	}

	@Override
	public Optional<Employee> findEmployeeByIdentityNo(IdentityNo id) {
		var employeeDocument = employeeDocumentRepository.findById(id.getValue());
		if (employeeDocument.isEmpty())
			return Optional.empty();
		return Optional.of(modelMapper.map(employeeDocument.get(), Employee.class));

	}

	@Override
	public List<Employee> findAllEmployeesByDepartment(Department department) {
		return employeeDocumentRepository.findAllByDepartment(department)
				.stream()
				.map(document -> modelMapper.map(document, Employee.class))
				.toList();
	}
	
}
