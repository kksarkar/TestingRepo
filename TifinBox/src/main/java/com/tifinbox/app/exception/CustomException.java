package com.tifinbox.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException
{
	public CustomException(String exception) {
		super(exception);
	}
}
