package com.bank.account.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bank.account.dto.TransactionEventDTO;
import com.bank.account.entity.Account;
import com.bank.account.service.AccountService;

@Component
public class TransactionConsumer {

	private static final  Logger log = LoggerFactory.getLogger(TransactionConsumer.class);
	
	private final AccountService accountService;
	
	public TransactionConsumer(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@KafkaListener(topics = "transaction-topic", groupId = "account-group")
	public void consumerTransaction(TransactionEventDTO event) {
		
		log.info("Received transaction event: {}", event);
		
		accountService.processTransaction(event);
	}
}
