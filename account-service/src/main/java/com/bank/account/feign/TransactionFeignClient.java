package com.bank.account.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction-service")
public interface TransactionFeignClient {

	@PutMapping("/transactions/{transactionId}/status")
	void updateTransactionStatus(@PathVariable Long transactionId,
								@RequestParam String status
								);
}
