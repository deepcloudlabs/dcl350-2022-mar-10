package com.example.hr.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.Department;

import reactor.core.publisher.Flux;

public interface EmployeeDocumentRepository extends ReactiveMongoRepository<EmployeeDocument, String> {

	Flux<EmployeeDocument> findAllByDepartment(Department department);

}
