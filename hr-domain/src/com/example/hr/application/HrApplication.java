package com.example.hr.application;

import java.util.List;
import java.util.Optional;

import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;

public interface HrApplication {
	Optional<Employee> hireEmployee(Employee employee);

	Optional<Employee> fireEmployee(IdentityNo id);

	Optional<Employee> getEmployeeInformation(IdentityNo id);

	List<Employee> increaseSalary(Department department, double rate);
}
