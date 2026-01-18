package com.reservation.system;

// represents Deluxe room category rooms
public class DeluxeRoom extends Room {
	// constructor initialize with name, description cost and room type.
	public DeluxeRoom(String roomName, String description, double costPerDay, String roomType) {
		super(roomName, description, costPerDay, roomType, "Deluxe");
	}

	@Override
	public double determinePrice() {
		// return cost per day for deluxe room
		return this.getCostPerDay();
	}
}
