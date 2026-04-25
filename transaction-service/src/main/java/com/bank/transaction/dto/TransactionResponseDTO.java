package com.bank.transaction.dto;

import java.time.LocalDateTime;

public class TransactionResponseDTO {

	private Long transactionID;
	private Long accountId;
	private Double amount;
	private String type;
	private String status;
	private LocalDateTime createdAt;
	
	
	public TransactionResponseDTO() {
		// TODO Auto-generated constructor stub
	}


	public TransactionResponseDTO(Long transactionID, Long accountId, Double amount, String type, String status,
			LocalDateTime createdAt) {
		super();
		this.transactionID = transactionID;
		this.accountId = accountId;
		this.amount = amount;
		this.type = type;
		this.status = status;
		this.createdAt = createdAt;
	}


	public Long getTransactionID() {
		return transactionID;
	}


	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}


	public Long getAccountId() {
		return accountId;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
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
