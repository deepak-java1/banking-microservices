package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByFirstNameContainingIgnoreCase(String name);
	
}
