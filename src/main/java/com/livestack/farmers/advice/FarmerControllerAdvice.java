package com.livestack.farmers.advice;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.livestack.farmers.custom.exception.PasswordAndConfirmPasswordNotMatch;
import com.livestack.farmers.custom.exception.EmptyInputException;
import com.livestack.farmers.custom.exception.FarmerExistsException;
import com.livestack.farmers.domain.FarmerException;
@ControllerAdvice
public class FarmerControllerAdvice {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String nowDate = dateFormat.format(timestamp);
	
	
	@Autowired
	private Environment env;

	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<FarmerException> handleEmptyInput(EmptyInputException exception, WebRequest request) {

		FarmerException farmerException = new FarmerException(nowDate, env.getProperty("empty.input.exception.msg"),
				request.getDescription(false), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(farmerException);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<FarmerException> noSuchUsernameExists(NoSuchElementException exception, WebRequest request) {
		FarmerException farmerException = new FarmerException(nowDate,
				env.getProperty("no.such.element.exception.msg"), request.getDescription(false),
				HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmerException);
	}

	@ExceptionHandler(FarmerExistsException.class)
	public ResponseEntity<FarmerException> handleFarmerExists(FarmerExistsException exception, WebRequest request) {
		FarmerException farmerException = new FarmerException(nowDate, env.getProperty("record.exists.msg"),
				request.getDescription(false), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(farmerException);
	}
	
	@ExceptionHandler(PasswordAndConfirmPasswordNotMatch.class)
	public ResponseEntity<FarmerException> passwordAndConfirmPasswordNotMatch(PasswordAndConfirmPasswordNotMatch exception, WebRequest request) {
		FarmerException farmerException = new FarmerException(nowDate, env.getProperty("password.not.match.msg"),
				request.getDescription(false), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(farmerException);
	}

	// handling custom validations here

	@ExceptionHandler({ BindException.class, MethodArgumentNotValidException.class })
	public ResponseEntity<Map<String, Object>> customValidationErrorHandling(BindException e, WebRequest request) {

		List<String> errors = new ArrayList<>();
		e.getFieldErrors().forEach(err -> errors.add(err.getField() + ": " + err.getDefaultMessage()));
		e.getGlobalErrors().forEach(err -> errors.add(err.getObjectName() + ": " + err.getDefaultMessage()));

		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", nowDate);
		errorResponse.put("message", errors);
		errorResponse.put("details", request.getDescription(false));
//		errorResponse.put("message", e.getLocalizedMessage());
		errorResponse.put("errorCode", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<FarmerException> methodNotAllowedHandling(HttpRequestMethodNotSupportedException exception, WebRequest request) {
		FarmerException farmerException = new FarmerException(nowDate,
				env.getProperty("no.such.method.allowed.msg"), request.getDescription(false),
				HttpStatus.METHOD_NOT_ALLOWED.value());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(farmerException);
	}

}
