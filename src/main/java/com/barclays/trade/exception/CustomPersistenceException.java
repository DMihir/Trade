package com.barclays.trade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mihir
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomPersistenceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPersistenceException() {
		super();
	}

	public CustomPersistenceException(String message) {
		super(message);
	}

	public CustomPersistenceException(Exception e) {
		super(e);
	}

	public CustomPersistenceException(String message, Exception e) {
		super(message, e);
	}

}
