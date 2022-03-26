package com.example.hr.application;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;

public interface HrApplication {
	CompletableFuture<Optional<Employee>> hireEmployee(Employee employee);

	CompletableFuture<Optional<Employee>> fireEmployee(IdentityNo id);

	CompletableFuture<Optional<Employee>> getEmployeeInformation(IdentityNo id);

	CompletableFuture<List<Employee>> increaseSalary(Department department, double rate);
}
