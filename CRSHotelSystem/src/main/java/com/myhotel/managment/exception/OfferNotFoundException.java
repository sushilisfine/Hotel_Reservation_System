package com.myhotel.managment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public OfferNotFoundException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public OfferNotFoundException(String message) {
		this(message, "Please Try with other id");
	}

	public OfferNotFoundException(Long offerId) {
		this("Offer Not Found for id: " + offerId, "Please Try Again");
	}

	public OfferNotFoundException() {
		this("Offer Not Found", "Please Try Again");
	}
}