package com.barclays.trade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mihir
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomException() {
		super();
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Exception e) {
		super(e);
	}

	public CustomException(String message, Exception e) {
		super(message, e);
	}

}
