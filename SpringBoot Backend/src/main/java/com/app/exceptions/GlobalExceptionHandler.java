package com.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.app.exceptions.list.BadRequestException;
import com.app.exceptions.list.InternalServerErrorException;
import com.app.exceptions.list.InvalidDataException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.exceptions.list.UnauthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle InvalidProductDataException
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<?> handleInvalidProductDataException(InvalidDataException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// Handle InternalServerErrorException
	@ExceptionHandler(value = InternalServerErrorException.class)
	public ResponseEntity<?> handleException(InternalServerErrorException ex, WebRequest request) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Handle NotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getMessage(),
				request.getDescription(false));
//		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errorDetails.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle NotFoundException
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle Exception
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", ex.getValue(),
				ex.getName(), ex.getRequiredType().getSimpleName());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

}
