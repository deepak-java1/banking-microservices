package com.bank.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.account.dto.CustomerDto;

@FeignClient(name = "customer-service")
public interface CustomerClient {

	@GetMapping("api/customers/{id}")
	CustomerDto getCustomerById(@PathVariable Long id);
}
