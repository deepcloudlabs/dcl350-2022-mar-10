package com.example.hr.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;

public interface EmployeeRepository {

	CompletableFuture<Boolean> exists(IdentityNo identity);

	CompletableFuture<Optional<Employee>> createEmployee(Employee employee);

	CompletableFuture<Optional<Employee>> removeEmployee(IdentityNo id);

	CompletableFuture<Optional<Employee>> findEmployeeByIdentityNo(IdentityNo id);

	CompletableFuture<List<Employee>> findAllEmployeesByDepartment(Department department);

}
