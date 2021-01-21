package com.myhotel.managment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public CategoryNotFoundException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public CategoryNotFoundException(String message) {
		this(message, "Please Try with other id");
	}

	public CategoryNotFoundException(Long categoryId) {
		this("Category Not Found for id: " + categoryId, "Please Try Again");
	}

	public CategoryNotFoundException() {
		this("Category Not Found", "Please Try Again");
	}
}