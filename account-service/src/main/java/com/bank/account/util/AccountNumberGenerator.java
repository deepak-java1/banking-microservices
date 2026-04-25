package com.bank.account.util;

import com.bank.account.client.CustomerClient;
import com.bank.account.repository.AccountRepository;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {

	private final AccountRepository repository;
	
	public AccountNumberGenerator(AccountRepository repository) {
		this.repository = repository;
	}
	
	public String generateAccountNumber(String accountType) {
		
		String typeCode;
		switch(accountType.toUpperCase()) {
		case "SAVINGS":
			typeCode = "01";
			break;
		case "CURRENT":
			typeCode ="02";
			break;
		case "FIXED":
			typeCode = "03";
			break;
		case "RECURRING":
			typeCode ="04";
			break;
		default:
			throw new RuntimeException("Invalid account Type");
		}
		
		// Get last account for this
		
		String lastAccountNumber = repository.findLastAccountNumberByType(accountType);
		
		int nextSequence = 1;
		
		if(lastAccountNumber != null) {
			String lastSeqStr = lastAccountNumber.substring(lastAccountNumber.length() - 2);
					nextSequence = Integer.parseInt(lastSeqStr) + 1;
		}
		
		// Format Sequence 
		String formattedSeq = String.format("%02d", nextSequence);
		
		// Build account number 
		return "ACC" + typeCode + "00" + formattedSeq;
		}
}
