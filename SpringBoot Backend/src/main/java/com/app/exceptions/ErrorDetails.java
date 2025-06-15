package com.app.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorDetails {

	private HttpStatus status;
	private String message;
	private String details;

	public ErrorDetails(HttpStatus status, String message, String details) {
		this.status = status;
		this.message = message;
		this.details = details;
	}
}
