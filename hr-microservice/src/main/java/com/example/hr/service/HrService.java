package com.example.hr.service;

import org.springframework.stereotype.Service;

import com.example.hr.application.HrApplication;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.HireEmployeeResponse;

@Service
public class HrService {
	private HrApplication hrApplication;
	
	private HrService(HrApplication hrApplication) {
		this.hrApplication = hrApplication;
	}

	public HireEmployeeResponse hireEmployee(HireEmployeeRequest request) {
		return null;
	}

}
