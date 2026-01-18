package com.reservation.system;

public abstract class Room {
	// Static field to generate unique room IDs
	private static int nextID = 101;
	// Instance variables to store room details
	private int roomID;
	private String roomName;
	private String description;
	private double costPerDay;
	private boolean isOccupied;
	private String guestID;
	private int numDays;
	private String roomType;
	private String roomCategory;

	// constructor to initialize the room with given attributes
	public Room(String roomName, String description, double costPerDay, String roomType, String roomCategory) {
		// Room category validation
		if (!roomCategory.equalsIgnoreCase("Standard") && !roomCategory.equalsIgnoreCase("Deluxe")
				&& !roomCategory.equalsIgnoreCase("Premium")) {
			// Throw an exception if the category is invalid
			throw new IllegalArgumentException(
					"Invalid room category: " + roomCategory + ". Please enter 'Standard', 'Deluxe', or 'Premium'.");
		}

		// assign a unique ID to the room and initialize other attributes
		this.roomID = nextID++;
		this.roomName = roomName;
		this.description = description;
		this.costPerDay = costPerDay;
		this.roomType = roomType;
		this.roomCategory = roomCategory;
		this.isOccupied = false; // room not occupied
	}

	// abstract method to determine the price of the room
	public abstract double determinePrice();

	// method to check in a guest to the room
	public void checkinRoom(String guestID, int numDays) throws AccommodationException {
		// check if the room is already occupied
		if (!isOccupied) {
			this.guestID = guestID;
			this.numDays = numDays;
			this.isOccupied = true;
		} else {
			// throws an exception if the room is already occupied
			throw new AccommodationException("Room is already occupied.");
		}
	}

	// method to check out a guest from the room
	public void checkoutRoom() throws AccommodationException {
		// check if the room is occupied
		if (isOccupied) {
			this.guestID = null; // clear the guest ID
			this.numDays = 0; // reset the number of days
			this.isOccupied = false; // mark the room as not occupied
		} else {
			// Throw an exception if the room is not occupied
			throw new AccommodationException("Room is not occupied.");
		}
	}

	// method to display information about the room
	public String displayRoomInfo() {
        String status = isOccupied ? "Yes" : "No"; //display yes or no if occupied or not
        return "-------------------" +
                "\nRoom ID      : " + roomID + 
                "\nName         : " + roomName + 
                "\nDescription  : " + description + 
                "\nCost         : $" + costPerDay + 
                "\nOccupied     : " + status +
                "\nType         : " + roomType +
                "\nCategory     : " + roomCategory + 
                "\n";
    }

	// Getters for room attributes
	public int getRoomID() {
		return roomID;
	}

	public String getRoomName() {
		return roomName;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public double getCostPerDay() {
		return costPerDay;
	}

	public String getRoomType() {
		return roomType;
	}

	public String getRoomCategory() {
		return roomCategory;
	}

	public String getDescription() {
		return description;
	}
}
