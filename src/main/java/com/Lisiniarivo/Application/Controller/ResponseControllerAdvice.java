package com.Lisiniarivo.Application.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.Lisiniarivo.Application.Core.ErrorResponse;
import com.Lisiniarivo.Application.Core.ErrorType;
import com.Lisiniarivo.Application.Exception.ResourceNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ResponseControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {
		Map<String ,String> errorMessage = new HashMap<>();
		errorMessage.put("error", exception.getMessage());
		ErrorResponse errorResponse = ErrorResponse.builder()
				.statusCode(404)
				.errorType(ErrorType.RESOURCE_NOT_FOUND)
				.details(errorMessage)
				.suggestion("Please make sure that informations you have entered are correct")
				.timeStamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> inputValidationException(ConstraintViolationException exception) {
		Map<String, String> errors = new HashMap<>();
		exception.getConstraintViolations()
		.forEach(error -> errors.put(String.valueOf(error.getPropertyPath()), error.getMessageTemplate()));
		
		ErrorResponse errorResponse = ErrorResponse.builder()
				.statusCode(400)
				.details(errors)
				.errorType(ErrorType.INPUT_MISMATCH)
				.suggestion(exception.getMessage())
				.timeStamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> methodArgumentType(MethodArgumentTypeMismatchException exception) {
		Map<String, String> errors = new HashMap<>();
		errors.put("errors", exception.getMessage());
		
		ErrorResponse errorResponse = ErrorResponse.builder()
				.statusCode(400)
				.errorType(ErrorType.INPUT_MISMATCH)
				.details(errors)
				.suggestion("Make that all your parameters are corrects")
				.timeStamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodInvalid(MethodArgumentNotValidException exception) {
		Map<String ,String> errors = new HashMap<>();
		exception.getBindingResult().getFieldErrors()
		.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		
		ErrorResponse errorResponse = ErrorResponse.builder()
				.statusCode(400)
				.errorType(ErrorType.INPUT_MISMATCH)
				.details(errors)
				.suggestion("Make sure that all datas are corrects")
				.timeStamp(LocalDateTime.now())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException exception) {
		Map<String, String> errorMessage = new HashMap<>();
		errorMessage.put("error", exception.getMessage());
		ErrorResponse errorResponse = ErrorResponse.builder()
				.statusCode(400)
				.errorType(ErrorType.INPUT_MISMATCH)
				.details(errorMessage)
				.suggestion("Please make sure that the given value is correct")
				.timeStamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorResponse> ioException(IOException exception) {
		Map<String, String> errorMessage = new HashMap<>();
		errorMessage.put("error", exception.getMessage());
		
		ErrorResponse errorResponse = ErrorResponse.builder()
				.statusCode(400)
				.errorType(ErrorType.FILE_UPLOAD_ERROR)
				.details(errorMessage)
				.suggestion("Make sure that your file match with the requirement")
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	
}
