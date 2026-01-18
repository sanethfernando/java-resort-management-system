package com.reservation.system;

//class for handling accommodation-related exceptions.
public class AccommodationException extends Exception {

	// constructor to pass the error message
	public AccommodationException(String message) {
		super(message); // calling parent class (Exception) constructor with the message.
	}
}

