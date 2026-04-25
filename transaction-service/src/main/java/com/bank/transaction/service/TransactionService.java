package com.bank.transaction.service;

import com.bank.transaction.dto.TransactionRequestDTO;
import com.bank.transaction.dto.TransactionResponseDTO;

public interface TransactionService {

	TransactionResponseDTO createTransaction(TransactionRequestDTO request);
	void updateTransactionStatus(Long transactionId, String status);
	
	
	
	
}
