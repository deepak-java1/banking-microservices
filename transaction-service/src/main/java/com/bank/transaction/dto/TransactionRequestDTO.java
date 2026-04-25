package com.bank.transaction.dto;

public class TransactionRequestDTO {

	private Long accountId;
	private Double amount;
	private String type;
	
	public TransactionRequestDTO() {
		
	}

	public TransactionRequestDTO(Long accountId, Double amount, String type) {
		this.accountId = accountId;
		this.amount = amount;
		this.type = type;
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
	
	
	
}
