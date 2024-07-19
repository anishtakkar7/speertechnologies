package com.speertechnologies.exception;

public class UserServiceException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorCode ;

	public UserServiceException(String errorMsg, String errorCode) {
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