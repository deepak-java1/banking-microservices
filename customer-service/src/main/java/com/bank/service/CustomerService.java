package com.bank.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import com.bank.dto.CreateCustomerRequest;
import com.bank.dto.UploadResponse;
import com.bank.entity.Customer;

public interface CustomerService {

	Customer createCustomer(CreateCustomerRequest request);
	Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
	UploadResponse uploadResponse(MultipartFile file) throws IOException;
	Page<Customer> getCustomers(int page, int size);
	Customer updateCustomer(Long id, CreateCustomerRequest request);
	Customer patchCustomer(Long id, Map<String, Object> updates);
	List<Customer> getCustomerByName(String name);
	void deleteCustomer(Long id);
	
	
}
