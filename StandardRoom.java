package com.reservation.system;

// standard room class inherited from the Room class
public class StandardRoom extends Room {

	// constructor for creating StandardRoom object
	public StandardRoom(String roomName, String description, double costPerDay, String roomType, String roomCategory) {
		// calling the superclass (Room) constructor to initialize the room's attributes
		super(roomName, description, costPerDay, roomType, roomCategory);
	}

	// overriding the abstract determinePrice method from the Room class
	@Override
	public double determinePrice() {
		// return the cost per day as the price for standard room
		return this.getCostPerDay();
	}
}
