package com.speertechnologies.exception;

import lombok.Data;

public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode ;
	
	public NotFoundException(String errorMsg, String errorCode) {
		super(errorMsg);
		this.errorCode = errorCode;
		
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
