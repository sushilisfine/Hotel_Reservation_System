package com.myhotel.managment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputMisMatachException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public InputMisMatachException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public InputMisMatachException(String message) {
		this(message, "Please Try with other inputs");
	}

	public InputMisMatachException() {
		this("Input Mis match ", "Please Try Again");
	}
}