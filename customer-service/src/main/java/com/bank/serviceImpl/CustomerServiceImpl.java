package com.bank.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dto.CreateCustomerRequest;
import com.bank.dto.UploadResponse;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.service.CustomerService;
import com.bank.util.CustomerExcelParser;
@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository repository;
	private final CustomerExcelParser excelParser;
	
	public CustomerServiceImpl(CustomerRepository repository,
								CustomerExcelParser excelParser) {
		this.repository = repository;
		this.excelParser = excelParser;
	}
	
	@Override
	public Customer createCustomer(CreateCustomerRequest request) {
		
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setMobile(request.getMobile());
		customer.setAddress(request.getAddress());
		customer.setCity(request.getAddress());
		customer.setStatus(request.getStatus());
		
		return repository.save(customer);
	}

	@Override
	public Customer getCustomerById(Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found..."));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return repository.findAll();
	}

	@Override
	public UploadResponse uploadResponse(MultipartFile file) throws IOException {
		List<Customer> customers = excelParser.parseExcel(file);
		
		repository.saveAll(customers);
		
		UploadResponse response = new UploadResponse();
		response.setTotal(customers.size());
		response.setSuccess(customers.size());
		response.setFailed(0);
		
		return response;
	}

	@Override
	public Page<Customer> getCustomers(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		
		return repository.findAll(pageable);
	}

	@Override
	public Customer updateCustomer(Long id, CreateCustomerRequest request) {
		
		Customer customer = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setMobile(request.getMobile());
		customer.setAddress(request.getAddress());
		customer.setCity(request.getCity());
		customer.setStatus(request.getStatus());
		
		return repository.save(customer);
	}

	@Override
	public Customer patchCustomer(Long id, Map<String, Object> updates) {

		Customer customer = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		
		if(updates.containsKey("email")) {
			customer.setEmail((String) updates.get("email"));
		}
		
		if(updates.containsKey("mobile")) {
			customer.setMobile((String) updates.get("mobile"));
		}
		return repository.save(customer);
	}

	@Override
	public List<Customer> getCustomerByName(String name) {

		return repository.findByFirstNameContainingIgnoreCase(name);
	}

	@Override
	public void deleteCustomer(Long id) {

		Customer customer = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		
		repository.delete(customer);
		
	}

}
