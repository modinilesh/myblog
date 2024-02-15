package com.springboot.blog.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.springboot.blog.payload.ErrorDetails;

@ControllerAdvice // used for handling exceptions globally
public class GlobalExceptionHandler {

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

}
