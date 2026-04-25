package com.bank.transaction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.transaction.dto.TransactionRequestDTO;
import com.bank.transaction.dto.TransactionResponseDTO;
import com.bank.transaction.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService service;
	
	public TransactionController(TransactionService service) {
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<TransactionResponseDTO> createTransaction(
									@RequestBody TransactionRequestDTO request){
		
		TransactionResponseDTO response = service.createTransaction(request);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{transactionId}/status")
	public ResponseEntity<Void> updateTransactionStatus(@PathVariable Long transactionId,
														@RequestParam String status){
		service.updateTransactionStatus(transactionId, status);
		return ResponseEntity.ok().build();
	}
	
	
	
}
