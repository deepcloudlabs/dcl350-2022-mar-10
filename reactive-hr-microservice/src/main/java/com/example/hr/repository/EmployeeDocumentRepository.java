package com.example.hr.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.Department;

public interface EmployeeDocumentRepository extends MongoRepository<EmployeeDocument, String> {

	List<EmployeeDocument> findAllByDepartment(Department department);

}
