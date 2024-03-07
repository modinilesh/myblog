package com.springboot.blog.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.payload.ErrorDetails;

@ControllerAdvice // used for handling exceptions globally
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// handle specific exceptions
	@ExceptionHandler(ResourceNotFound.class) // used to handle specific exceptions
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound rnfException,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), rnfException.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BlogAPIException.class) // used to handle specific exceptions
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException baException,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), baException.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	//handling Global Exceptions
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception e,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	//handling Validations //first approach
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
															   HttpHeaders headers, 
															   HttpStatusCode status, 
															   WebRequest request) {
		Map<String, String> response = new LinkedHashMap<>();
				
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName, message);
		});
		response.put("time", (new Date()).toString());
		
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	//2nd approach
//	@ExceptionHandler(MethodArgumentNotValidException.class) 
//	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
//															   WebRequest webRequest) 
//	{
//		Map<String, String> response = new HashMap<>();
//		
//		e.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError)error).getField();
//			String message = error.getDefaultMessage();
//			response.put(fieldName, message);
//		});
//		response.put("time", (new Date()).toString());
//		
//		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//	}


}
