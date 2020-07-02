package com.tifinbox.app.exception;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tifinbox.app.model.ExceptionResponse;


@RestControllerAdvice

public class CustomGlobalExceptionHandler
{
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  @ExceptionHandler(ResourceNotFoundException.class)
	  public ExceptionResponse handleNotFoundException(ResourceNotFoundException ex) 
	  {
		ExceptionResponse responseMsg = new ExceptionResponse(new Date(), ex.getMessage());
	    return responseMsg;
	  }
	  @ResponseStatus(HttpStatus.CONFLICT)
	  @ExceptionHandler(ResourceAlredyExistException.class)
	  public ExceptionResponse handleNotFoundException(ResourceAlredyExistException ex) 
	  {
		ExceptionResponse responseMsg = new ExceptionResponse(new Date(), ex.getMessage());
	    return responseMsg;
	  }
	  	  
	  
	  
	  /*
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  @ExceptionHandler(ResourceNotFoundException.class)
	  public ExceptionResponse handleNotFoundException(ResourceNotFoundException ex, WebRequest request) 
	  {
		ExceptionResponse responseMsg = new ExceptionResponse(new Date(), ex.getMessage(),request.getDescription(false));
	    return responseMsg;
	  }
	  
	 @ExceptionHandler(Exception.class)
	 public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) 
	 {
	    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),request.getDescription(false));
	    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	 
	 @ExceptionHandler(ResourceNotFoundException.class)
	 public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) 
	 {
	     ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),request.getDescription(false));
	     return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	 }*/
}
