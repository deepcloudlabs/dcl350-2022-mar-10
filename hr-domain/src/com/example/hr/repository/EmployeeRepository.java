package com.example.hr.repository;

import java.util.List;
import java.util.Optional;

import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.IdentityNo;

public interface EmployeeRepository {

	boolean exists(IdentityNo identity);

	Employee createEmployee(Employee employee);

	Optional<Employee> removeEmployee(IdentityNo id);

	Optional<Employee> findEmployeeByIdentityNo(IdentityNo id);

	List<Employee> findAllEmployeesByDepartment(Department department);

}
