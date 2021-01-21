package com.myhotel.managment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public HotelNotFoundException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public HotelNotFoundException(String message) {
		this(message, "Please Try with other id");
	}

	public HotelNotFoundException(Long hotelId) {
		this("Hotel Not Found for id: " + hotelId, "Please Try Again");
	}

	public HotelNotFoundException() {
		this("Hotel Not Found", "Please Try Again");
	}
}