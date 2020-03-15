package com.yoho.errorHandlers;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null)
			errorMessageDescription = ex.toString();
		System.out.println(errorMessageDescription);
		System.err.println(errorMessageDescription);
		System.err.println("Message => " + ex.getMessage());
		System.err.println("Cause => " + ex.getCause());
		System.err.println("Description => " + ex.toString());
		ex.printStackTrace();
		ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
