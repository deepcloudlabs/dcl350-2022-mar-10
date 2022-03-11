package com.example.hr.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeTest {

	@Test
	void test() {
		var jack = new Employee.Builder("11111111110")
				.iban("tr1")
				.department("IT")
				.fullname("jack", "bauer")
				.salary(10_000, FiatCurrency.EUR)
				.jobStyle("FULL_TIME")
				.birthYear(1965)
				.build();
		assertEquals("jack", jack.getFullname().getFirstName());
		assertEquals("bauer", jack.getFullname().getLastName());
	}

}
