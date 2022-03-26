package com.example.hr.adapter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
	public CompletableFuture<Boolean> exists(IdentityNo identity) {
		return employeeDocumentRepository.existsById(identity.getValue()).toFuture();
	}

	@Override
	public CompletableFuture<Optional<Employee>> createEmployee(Employee employee) {
		System.err.println(employee);
		var document = modelMapper.map(employee, EmployeeDocument.class);
		System.err.println(document);
		var newEmployeeDocument = employeeDocumentRepository.save(document);
		return newEmployeeDocument.map( newEmpDoc -> Optional.ofNullable(modelMapper.map(newEmpDoc, Employee.class))).toFuture();
	}

	@Override
	public CompletableFuture<Optional<Employee>> removeEmployee(IdentityNo id) {
		return employeeDocumentRepository.findById(id.getValue())
		    .map(empDoc -> {
		    	employeeDocumentRepository.delete(empDoc).subscribe(removedEmpDoc -> System.out.println("Employee is removed."));
		    	return Optional.of(modelMapper.map(empDoc, Employee.class));
		    }).toFuture();
	}

	@Override
	public CompletableFuture<Optional<Employee>> findEmployeeByIdentityNo(IdentityNo id) {
		return employeeDocumentRepository.findById(id.getValue()).map( empDoc -> {
			return Optional.of(modelMapper.map(empDoc, Employee.class));			
		}).toFuture();
	}

	@Override
	public CompletableFuture<List<Employee>> findAllEmployeesByDepartment(Department department) {
		return CompletableFuture.supplyAsync( 
				() -> employeeDocumentRepository.findAllByDepartment(department)
												.map(document -> modelMapper.map(document, Employee.class))
												.toStream()
												.toList()
			 );
	}
	
}
