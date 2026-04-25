package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerResponse {

	private Long customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String address;
	private String city;
	private String status;
	
	public CreateCustomerResponse(Long customerId, String firstName, String lastName, String email, String mobile,
			String address, String city, String status) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.city = city;
		this.status = status;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getMobile() {
		return mobile;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getStatus() {
		return status;
	}
	
	
	
}
