package com.speertechnologies.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.speertechnologies.exception.NotFoundException;
import com.speertechnologies.exception.UserServiceException;
import com.speertechnologies.model.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception)
	{
		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
				.errorCode(exception.getErrorCode())
				.errorMessage(exception.getMessage()).build(), HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler(UserServiceException.class)
	public ResponseEntity<ErrorResponse> handleUnAuthorizedUserException(UserServiceException exception)
	{
		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
				.errorCode(exception.getErrorCode())
				.errorMessage(exception.getMessage()).build(), HttpStatus.UNAUTHORIZED); 
	}
	
//	@ExceptionHandler(UserServiceException.class)
//	public ResponseEntity<ErrorResponse> handleUnAuthenticatedUserException(UserServiceException exception)
//	{
//		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
//				.errorCode(exception.getErrorCode())
//				.errorMessage(exception.getMessage()).build(), HttpStatus.FORBIDDEN ); 
//	}
}
