package com.bank.account.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.account.client.CustomerClient;
import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.dto.CustomerDto;
import com.bank.account.dto.DepositRequest;
import com.bank.account.dto.DepositResponse;
import com.bank.account.dto.TransactionEventDTO;
import com.bank.account.dto.WithdrawRequest;
import com.bank.account.dto.WithdrawResponse;
import com.bank.account.entity.Account;
import com.bank.account.feign.TransactionFeignClient;
import com.bank.account.repository.AccountRepository;
import com.bank.account.service.AccountService;
import com.bank.account.util.AccountNumberGenerator;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	private final CustomerClient customerClient;
	private final AccountRepository repository;
	private final AccountNumberGenerator generator;
	private final TransactionFeignClient transactionFeignClient;
	
	public AccountServiceImpl(CustomerClient customerClient,
								AccountRepository repository,
								AccountNumberGenerator generator,
								TransactionFeignClient transactionFeignClient) {
		this.customerClient  = customerClient;
		this.repository = repository;
		this.generator = generator;
		this.transactionFeignClient = transactionFeignClient;
			
	}

	@Override
	public AccountResponse createAccount(CreateAccountRequest request) {
		
		// here validating customer using feign client
		CustomerDto customer = customerClient.getCustomerById(request.getCustomerId());
		
		if(customer == null) {
			throw new RuntimeException("Customer not found");
		}
		
		// Generating account Number 
		String accountNumber = generator.generateAccountNumber(request.getAccountType());
		
		
		// creating account
		
		Account account = new Account();
		account.setCustomerId(request.getCustomerId());
		account.setAccountType(request.getAccountType());
		account.setAccountNumber(accountNumber);
		
		// saving
		Account saved = repository.save(account);
		
		// Response
		  AccountResponse response = new AccountResponse();
		    response.setAccountId(saved.getAccountId());
		    response.setAccountNumber(saved.getAccountNumber());
		    response.setCustomerId(saved.getCustomerId());
		    response.setAccountType(saved.getAccountType());
		    response.setStatus(saved.getStatus());
		    response.setBalance(saved.getBalance());

		    return response;
	}

	@Override
	public AccountResponse getAccountById(Long id) {
		Account account = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Accoun not found"));
		
		AccountResponse response = new AccountResponse();
		response.setAccountId(account.getAccountId());
		response.setAccountNumber(account.getAccountNumber());
		response.setCustomerId(account.getCustomerId());
		response.setAccountType(account.getAccountType());
		response.setAccountId(account.getAccountId());
		response.setStatus(account.getStatus());
		response.setBalance(account.getBalance());
		
		return response;
	}

	@Override
	public List<AccountResponse> getaccountByCustomerId(Long customerId) {
		List<Account> accounts = repository.findByCustomerId(customerId);
				
		return accounts.stream()
						.map(account -> {
							AccountResponse response = new AccountResponse();
							response.setAccountId(account.getAccountId());
							response.setAccountNumber(account.getAccountNumber());
							response.setCustomerId(account.getCustomerId());
							response.setAccountType(account.getAccountType());
							response.setStatus(account.getStatus());
							response.setBalance(account.getBalance());
							
							return response;
						}).toList();
	}

	@Override
	@Transactional
	public void freezeAccount(Long accountId) {
		Account account = repository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found"));
		
		account.setStatus("FROZEN");
		repository.save(account);
		
	}

	@Override
	@Transactional
	public void unFreezeAccount(Long accountId) {
		Account account = repository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not founds"));
		
		account.setStatus("ACTIVE");
		repository.save(account);
		
	}

	@Override
	public BigDecimal getBalance(Long accountId) {
		
		Account account = repository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found"));
		
		return account.getBalance();
	}

	@Override
	@Transactional
	public DepositResponse deposit(Long accountId, DepositRequest request) {
	log.info("Deposit request received for accountId : {}", accountId);
		
		Account account = repository.findByIdForUpdate(accountId);
		
		if(account == null) {
			log.error("Account not found for id :", accountId);
			throw new RuntimeException("Account not found");
		}
		
		if("FROZEN".equals(account.getStatus())) {
			log.warn("Deposit attempted on frozen Account :", accountId);
			throw new RuntimeException("Account is frozen");
		}
		
		account.setBalance(account.getBalance().add(request.getAmount()));
		repository.save(account);
		
		log.info("Deposit successful. AccountId: {}, Balance :", accountId, account.getBalance());
		
		DepositResponse response = new DepositResponse();
		response.setAccountId(account.getAccountId());
		response.setBalance(account.getBalance());
		response.setMessage("Deposite successful");
		
		return null;
	}

	@Transactional
	@Override
	public WithdrawResponse withdraw(Long accountId, WithdrawRequest request) {
	
		Account account = repository.findByIdForUpdate(accountId);
			
		if(account == null) {
			throw new RuntimeException("Account not found");
		}
				
		if("FROZEN".equals(account.getStatus())) {
			throw new RuntimeException("Account is frozen");
		}
		
		if(account.getBalance().compareTo(request.getAmount()) < 0 ) {
			throw new RuntimeException("Insufficient balance");
		}
		
		account.setBalance(account.getBalance().subtract(request.getAmount()));
		
		repository.save(account);
		
		WithdrawResponse response = new WithdrawResponse();
		response.setAccountId(account.getAccountId());
		response.setBalance(account.getBalance());
		response.setMessage("Withdrawal successful");
		
		return response;
	}

	@Transactional
	@Override
	public void setTransactionLimit(Long accountId, BigDecimal limit) {
		Account account = repository.findByIdForUpdate(accountId);
				
		if(account == null) {
			throw new RuntimeException("Account nor found");
		}
		
		account.setTransactionLimit(limit);
		
		repository.save(account);
		
	}

	@Override
	@Transactional
	public void processTransaction(TransactionEventDTO event) {
		
		Account account = repository.findById(event.getAccountId())
				.orElseThrow(() -> new RuntimeException("Account not found"));
		
		if("FROZEN".equalsIgnoreCase(account.getStatus())) {
			transactionFeignClient.updateTransactionStatus(event.getTransactionId(), "FAILED");
			return;
		}
		
		if("DEPOSIT".equalsIgnoreCase(event.getType())) {
			account.setBalance(account.getBalance().add(event.getAmount()));
		}
		
		if("WITHDRAW".equalsIgnoreCase(event.getType())) {
			if(account.getBalance().compareTo(event.getAmount()) < 0) {
				transactionFeignClient.updateTransactionStatus(event.getTransactionId(), "FAILED");
				return;
			}
			
			account.setBalance(account.getBalance().subtract(event.getAmount())
					);
		}
		repository.save(account);
		transactionFeignClient.updateTransactionStatus(event.getTransactionId(), "SUCCESS");
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
