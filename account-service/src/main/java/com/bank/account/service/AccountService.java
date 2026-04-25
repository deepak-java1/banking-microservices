package com.bank.account.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.dto.DepositRequest;
import com.bank.account.dto.DepositResponse;
import com.bank.account.dto.TransactionEventDTO;
import com.bank.account.dto.WithdrawRequest;
import com.bank.account.dto.WithdrawResponse;

public interface AccountService {

	AccountResponse createAccount(CreateAccountRequest request);
	AccountResponse getAccountById(Long id);
	List<AccountResponse> getaccountByCustomerId(Long customerId);
	void freezeAccount(Long accountId);
	void unFreezeAccount(Long accountId);
	BigDecimal getBalance(Long accountId);
	DepositResponse deposit(Long accountId, DepositRequest request);
	WithdrawResponse withdraw(Long accountId, WithdrawRequest request);
	void setTransactionLimit(Long accountId, BigDecimal limit);
	
	void processTransaction(TransactionEventDTO event);
}
