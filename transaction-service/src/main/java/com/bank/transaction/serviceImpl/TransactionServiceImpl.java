package com.bank.transaction.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.bank.transaction.dto.TransactionRequestDTO;
import com.bank.transaction.dto.TransactionResponseDTO;
import com.bank.transaction.entity.Transaction;
import com.bank.transaction.kafka.TransactionProducer;
import com.bank.transaction.repository.TransactionRepository;
import com.bank.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements  TransactionService {

	private final TransactionRepository repository;
	private final TransactionProducer producer;
	
	public TransactionServiceImpl(TransactionRepository repository,
								  TransactionProducer producer) {
		this.repository = repository;
		this.producer = producer;
	}

	@Override
	public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(request.getAccountId());
		transaction.setAmount(request.getAmount());
		transaction.setType(request.getType());
		transaction.setStatus("PENDING");
		transaction.setCreatedAt(LocalDateTime.now());
		
		Transaction savedTransaction = repository.save(transaction);
		
		producer.sendTransactionEvent(savedTransaction);
		
		return new TransactionResponseDTO(
				savedTransaction.getTransactionId(),
				savedTransaction.getAccountId(),
				savedTransaction.getAmount(),
				savedTransaction.getType(),
				savedTransaction.getStatus(),
				savedTransaction.getCreatedAt()
				);
	}

	@Override
	public void updateTransactionStatus(Long transactionId, String status) {
		Transaction transaction = repository
				.findById(transactionId)
				.orElseThrow(() -> new RuntimeException("Transaction not found"));
		
		transaction.setStatus(status);
		repository.save(transaction);
									
		
	}
}
