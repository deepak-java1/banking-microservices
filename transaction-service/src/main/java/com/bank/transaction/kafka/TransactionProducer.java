package com.bank.transaction.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.bank.transaction.entity.Transaction;

@Component
public class TransactionProducer {

	private final KafkaTemplate<String, Transaction> kafkaTemplate;
	
	public TransactionProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendTransactionEvent(Transaction transaction) {
		kafkaTemplate.send("transaction-topic", transaction);
	}
	
}
