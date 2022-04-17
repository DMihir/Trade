package com.barclays.trade.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.barclays.trade.dto.ApiError;

/**
 * @author Mihir
 *
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle record not found exception.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ApiError> handleUserNotFoundException(RecordNotFoundException ex,
			HttpServletRequest request) {
		List<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, errorList, "Record Not Found!", request.getRequestURI());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handle pessimistic lock exception.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CannotAcquireLockException.class)
	public ResponseEntity<Object> handleObjectOptimisticLockingFailureException(Exception ex,
			HttpServletRequest request) {
		List<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorList,
				"Row was updated or deleted by another transaction!", request.getRequestURI());
		return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Handle bad argument exception.
	 * 
	 * @see org.springframework.web.servlet.mvc.method.annotation.
	 * ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.
	 * springframework.web.bind.MethodArgumentNotValidException,
	 * org.springframework.http.HttpHeaders,
	 * org.springframework.http.HttpStatus,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errorList = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			errorList.add(error.getDefaultMessage());
		}
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorList, "Validation Failed!",
				((ServletWebRequest) request).getRequest().getRequestURI());
		return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle custom data persistence exception.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CustomPersistenceException.class)
	public ResponseEntity<ApiError> handleCustomPersistenceException(CustomPersistenceException ex,
			HttpServletRequest request) {
		List<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorList, "DB Error!", request.getRequestURI());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle custom exception in application.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiError> handleCustomException(CustomException ex, HttpServletRequest request) {
		List<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorList, "Custom Exception",
				request.getRequestURI());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle all other generic exception.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request) {
		ex.printStackTrace();
		List<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());

		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorList, "Server Error!",
				request.getRequestURI());
		return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
