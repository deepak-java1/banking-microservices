package com.bank.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionEventDTO {

	private Long transactionId;
	private Long accountId;
	private BigDecimal amount;
	private String type;
	private String status;
	private LocalDateTime createdAt;
	
	public TransactionEventDTO() {
	
	}

	public TransactionEventDTO(Long transactionId, Long accountId, BigDecimal amount, String type, String status,
			LocalDateTime createdAt) {
		super();
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.amount = amount;
		this.type = type;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
