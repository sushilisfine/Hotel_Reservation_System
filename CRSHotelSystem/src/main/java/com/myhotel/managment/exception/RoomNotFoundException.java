package com.myhotel.managment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String details;

	public RoomNotFoundException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public RoomNotFoundException(String message) {
		this(message, "Please Try with other id");
	}

	public RoomNotFoundException(Long roomId) {
		this("Room Not Found for id: " + roomId, "Please Try Again");
	}

	public RoomNotFoundException() {
		this("Room Not Found", "Please Try Again");
	}
}