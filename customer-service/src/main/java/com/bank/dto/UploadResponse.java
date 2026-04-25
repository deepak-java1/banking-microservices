package com.bank.dto;

public class UploadResponse {

	int total;
	int success;
	int failed;
	
	public UploadResponse() {
	}

	public UploadResponse(int total, int success, int failed) {
		super();
		this.total = total;
		this.success = success;
		this.failed = failed;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	@Override
	public String toString() {
		return "UploadResponse [total=" + total + ", success=" + success + ", failed=" + failed + "]";
	}
	
	
	
}
