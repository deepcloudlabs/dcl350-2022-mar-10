package com.example.crm.document;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class CustomerDocument {
	@Id
	private String identity;
	private String fullname;
	private String email;
	private String phone;
	private int birthYear;

	public CustomerDocument() {
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDocument other = (CustomerDocument) obj;
		return Objects.equals(identity, other.identity);
	}

	@Override
	public String toString() {
		return "CustomerDocument [identity=" + identity + ", fullname=" + fullname + ", email=" + email + ", phone="
				+ phone + ", birthYear=" + birthYear + "]";
	}

}
