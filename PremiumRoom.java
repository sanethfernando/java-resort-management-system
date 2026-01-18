package com.reservation.system;

//represents premium room category rooms
public class PremiumRoom extends Room {
	// constructor initialize with name, description cost and room type.
	public PremiumRoom(String roomName, String description, double costPerDay, String roomType) {
		super(roomName, description, costPerDay, roomType, "Premium");
	}

	@Override
	public double determinePrice() {
		// return cost per day for deluxe room
		return this.getCostPerDay();
	}
}
