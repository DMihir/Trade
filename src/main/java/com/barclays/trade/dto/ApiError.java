package com.barclays.trade.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Mihir
 * This class is created to handle custom exception response for user.
 */
@Data
public class ApiError {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private HttpStatus status;
	private List<String> message;
	private String error;
	private String path;

	public ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	public ApiError(HttpStatus status, List<String> message) {
		this();
		this.status = status;
		this.message = message;
	}

	public ApiError(HttpStatus status, List<String> message, String error) {
		this();
		this.status = status;
		this.message = message;
		this.error = error;
	}

	public ApiError(HttpStatus status, List<String> message, String error, String path) {
		this();
		this.status = status;
		this.message = message;
		this.error = error;
		this.path = path;
	}
}
