package com.bank.account.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.dto.DepositRequest;
import com.bank.account.dto.DepositResponse;
import com.bank.account.dto.LimitRequest;
import com.bank.account.dto.WithdrawRequest;
import com.bank.account.dto.WithdrawResponse;
import com.bank.account.service.AccountService;
import com.bank.account.util.ApiResponse;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request){
		
		AccountResponse response = accountService.createAccount(request);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/{accountId}/deposit")
	public ResponseEntity<DepositResponse> deposit(@PathVariable Long accountId,
													@RequestBody DepositRequest request ){
		
		return ResponseEntity.ok(accountService.deposit(accountId, request));
	}
	
	@PostMapping("/{accountId}/withdraw")
	public ResponseEntity<WithdrawResponse> withdraw(@PathVariable Long accountId,
													@RequestBody WithdrawRequest request){
		return ResponseEntity.ok(accountService.withdraw(accountId, request));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable Long id){
		AccountResponse data = accountService.getAccountById(id);
		
		ApiResponse<AccountResponse> response = new ApiResponse<>("SUCCESS","account fetched", data);
		
		return ResponseEntity.ok(response);
		// return ResponseEntity.ok(accountService.getAccountById(id));
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<AccountResponse>> getAccountByCustomerId(@PathVariable Long customerId){
		return ResponseEntity.ok(accountService.getaccountByCustomerId(customerId));
	}
	
	@GetMapping("/{accountId}/balance")
	public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId){
		
		return ResponseEntity.ok(accountService.getBalance(accountId));
	}
	
	@PutMapping("/{accountId}/freeze")
	public ResponseEntity<String> freezeAccount(@PathVariable Long accountId){
		
		accountService.freezeAccount(accountId);
		return ResponseEntity.ok("Account is Frozen");
	}
	
	@PutMapping("/{accountId}/unFreeze")
	public ResponseEntity<String> unFreezeAccount(@PathVariable Long accountId){
		
		accountService.unFreezeAccount(accountId);
		return ResponseEntity.ok("Account is Active");
	}
	
	@PutMapping("/{accountId}/limit")
	public ResponseEntity<String> setLimit(@PathVariable Long accountId,
											@RequestBody LimitRequest request){
		accountService.setTransactionLimit(accountId, request.getLimit());
		return ResponseEntity.ok("Transaction Limit Updated");
	}
	
	
	
}
