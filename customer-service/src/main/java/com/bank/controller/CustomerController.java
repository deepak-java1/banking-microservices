package com.bank.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dto.CreateCustomerRequest;
import com.bank.dto.CreateCustomerResponse;
import com.bank.entity.Customer;
import com.bank.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<CreateCustomerResponse> createCustomer(
			@RequestBody CreateCustomerRequest request){
		
		Customer customer = customerService.createCustomer(request);
		
		CreateCustomerResponse response = new CreateCustomerResponse(
				customer.getCustomerId(),
				customer.getFirstName(), 
				customer.getLastName(),
				customer.getEmail(),
				customer.getMobile(), 
				customer.getAddress(), 
				customer.getCity(), 
				customer.getStatus()
				);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/bulk/upload")
	public ResponseEntity<?> uploadCustomers(@RequestParam("file") MultipartFile file) throws IOException{
		return ResponseEntity.ok(customerService.uploadResponse(file));
	}
	
	@GetMapping("/allCustomers")
	public ResponseEntity<List<CreateCustomerResponse>> getAllCustomers(){
		
		List<Customer> customers = customerService.getAllCustomers();
		
		List<CreateCustomerResponse> response = customers.stream()
					.map(c -> new CreateCustomerResponse(
							c.getCustomerId(), 
							c.getFirstName(),
							c.getLastName(),
							c.getEmail(),
							c.getMobile(),
							c.getAddress(),
							c.getCity(),
							c.getStatus()
							))
							.toList();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> getCustomers(@RequestParam int page,
										  @RequestParam int size){
		return ResponseEntity.ok(customerService.getAllCustomers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
		return ResponseEntity.ok(customerService.getCustomerById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Customer>> getCustomerByName(@PathVariable String name){
		
		return ResponseEntity.ok(customerService.getCustomerByName(name));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,
												   @RequestBody CreateCustomerRequest request){
		
		return ResponseEntity.ok(
				customerService.updateCustomer(id, request));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Customer> patchCustomer(@PathVariable Long id,
												  @RequestBody Map<String, Object> update){
			return ResponseEntity.ok(
							customerService.patchCustomer(id, update));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
		
		customerService.deleteCustomer(id);
		return ResponseEntity.ok("Customer deleted successfully");
	}
	
}
