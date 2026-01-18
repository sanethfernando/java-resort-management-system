/**
 * Resort Management System
 * Author: Saneth Fernando
 * Description: Console-based Java application for managing
 * room check-in, check-out, and persistence using OOP principles.
 */

package com.reservation.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class StageD {
	private static final String DATA_FILE = "accommodations.txt"; // File to store room data
	private ArrayList<Room> accommodations; // list to hold Room objects

	public StageD() {
		accommodations = new ArrayList<>();
		loadData(); // Load data from file on startup
	}

	public static void main(String[] args) {
		StageD stageD = new StageD(); // creating an instance of StageD
		Scanner scanner = new Scanner(System.in);

		// main menu loop
		while (true) {
			System.out.println("\n--- Welcome to Resort By the Sea Booking System ---");
			System.out.println("1. Check-In");
			System.out.println("2. Check-Out");
			System.out.println("3. Display All Rooms Info");
			System.out.println("4. Display a Specific Room Info");
			System.out.println("5. Summary");
			System.out.println("6. Save and Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			try {
				switch (choice) {
				case 1:
					stageD.checkInRoom(scanner); // room check-in handeling
					break;

				case 2:
					stageD.checkOutRoom(scanner); // room check-out handeling
					break;

				case 3:
					stageD.displayRoomInfo(); // displaying all room information
					break;

				case 4:
					stageD.displaySpecificRoomInfo(scanner); // display info for a specific room
					break;

				case 5:
					stageD.displaySummary(); // display summary of rooms
					break;

				case 6:
					stageD.saveData(); // saving data to the file and exiting
					System.out.println("Exiting...");
					scanner.close();
					System.exit(0);

				default:
					System.out.println("Invalid choice. Please try again.");
					break;
				}

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage()); // handling invalid arguments
			} catch (AccommodationException e) {
				System.out.println("Error: " + e.getMessage());// handling booking exceptions
			} catch (Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage()); // catch all unexpected errors
			}
		}
	}

	public void checkInRoom(Scanner scanner) throws AccommodationException {
		// asking special services requirement
		System.out.print("Do you need a room with special services? (Standard double or Triple) \nyes/no: ");
		String specialRequest = scanner.nextLine().trim().toLowerCase();

		// prompt for room type
		System.out.print("Enter room type (Single/Double/Triple): ");
		String type = scanner.nextLine().trim();

		// prompt for room category
		System.out.print("Enter room category (Standard/Deluxe/Premium): ");
		String category = scanner.nextLine().trim();

		// Room category validation
		if (!category.equalsIgnoreCase("Standard") && !category.equalsIgnoreCase("Deluxe")
				&& !category.equalsIgnoreCase("Premium")) {
			throw new IllegalArgumentException(
					"Invalid room category: " + category + ". Please enter 'Standard', 'Deluxe', or 'Premium'.");
		}

		// prompt for number of days
		System.out.print("Enter number of days: ");
		int numDays = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Room availableRoom = null;
		// finding available room matches the criteria
		boolean roomFoundButOccupied = false;

		for (Room room : accommodations) {
			boolean matchesTypeAndCategory = room.getRoomType().equalsIgnoreCase(type)
					&& room.getRoomCategory().equalsIgnoreCase(category);

			boolean matchesSpecialRequest = (specialRequest.equals("yes") && room instanceof SpecialServices)
					|| (specialRequest.equals("no") && !(room instanceof SpecialServices));

			// Check if a matching room exists but is occupied
			if (matchesTypeAndCategory && matchesSpecialRequest) {
				if (room.isOccupied()) {
					roomFoundButOccupied = true; // Room matches but is occupied
				} else {
					availableRoom = room; // Room matches and is available
					break;
				}
			}
		}

		if (availableRoom != null) {
			// check in continues if room is available
			System.out.println("Room available: " + availableRoom.displayRoomInfo());
			System.out.print("Do you want to check in to this room? (yes/no): ");
			String confirmation = scanner.nextLine();

			if (confirmation.equalsIgnoreCase("yes")) {
				// check in the guest
				System.out.print("Enter guest ID: ");
				String guestID = scanner.nextLine();
				availableRoom.checkinRoom(guestID, numDays);
				System.out.println("Checked in successfully.");
				generateReceipt(guestID, availableRoom, numDays); // Generate receipt
			} else {
				System.out.println("Check-in canceled.");
			}
		} else if (roomFoundButOccupied) {
			throw new AccommodationException("Room is already occupied.");
		} else {
			System.out.println("No available room matches your criteria.");
		}
	}

	public void checkOutRoom(Scanner scanner) throws AccommodationException {
		// prompt for room Id to check out
		System.out.print("Enter room ID to check-out: ");
		int checkoutID = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Room roomToCheckOut = null;
		// finding the room with the provided ID
		for (Room room : accommodations) {
			if (room.getRoomID() == checkoutID) {
				roomToCheckOut = room;
				break;
			}
		}

		if (roomToCheckOut != null) {
			if (roomToCheckOut.isOccupied()) {
				// check-out the room if it's occupied
				roomToCheckOut.checkoutRoom();
				System.out.println("Checked out successfully.");
			} else {
				throw new AccommodationException("Room is not occupied.");// else displays room not occupied
			}
		} else {
			System.out.println("Room not found.");// else displays room not exist
		}
	}

	// generating receipt after successful check-in
	private void generateReceipt(String guestID, Room room, int numDays) {
		double totalCharge = room.getCostPerDay() * numDays;
		System.out.println("\n----- Receipt -----");
		System.out.println("Guest ID     : " + guestID);
		System.out.println("Room ID      : " + room.getRoomID());
		System.out.println("Number of Days: " + numDays);
		System.out.println("Total Charge : $" + totalCharge);
		System.out.println("-------------------\n");
	}

	public void displayRoomInfo() {
		int occupiedCount = 0;
		// displaying infor for all rooms
		for (Room room : accommodations) {
			System.out.println(room.displayRoomInfo());
			if (room.isOccupied()) {
				occupiedCount++;
			}
		}
		// display total number of occupied rooms
		System.out.println("Total number of rooms occupied: " + occupiedCount);
	}

	public void displaySpecificRoomInfo(Scanner scanner) {
		// prompt for room ID to display information
		System.out.print("Enter room ID to display information: ");
		int roomID = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Room roomToDisplay = null;
		// finding the room with provided ID
		for (Room room : accommodations) {
			if (room.getRoomID() == roomID) {
				roomToDisplay = room;
				break;
			}
		}

		if (roomToDisplay != null) {
			// display the room's information
			System.out.println(roomToDisplay.displayRoomInfo());
		} else {
			System.out.println("Room not found."); // when room doesn't exist
		}
	}

	// displaying the summary of available and occupied rooms
	public void displaySummary() {
		int occupiedCount = 0;

		// display all occupied rooms
		System.out.println("Currently Occupied Rooms:");
		for (Room room : accommodations) {
			if (room.isOccupied()) {
				System.out.println(room.displayRoomInfo());
				occupiedCount++;
			}
		}

		// displaying all available rooms
		System.out.println("\nCurrently Available Rooms:");
		for (Room room : accommodations) {
			if (!room.isOccupied()) {
				System.out.println(room.displayRoomInfo());
			}
		}

		// display total number of occupied rooms
		System.out.println("\nTotal number of rooms occupied: " + occupiedCount);
	}

	// Save room data to a file
	public void saveData() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
			// write each rooms's data to the file
			for (Room room : accommodations) {
				writer.println(room.getRoomID() + "|" + room.getRoomName() + "|" + room.getRoomType() + "|"
						+ room.getRoomCategory() + "|" + room.getCostPerDay() + "|" + room.isOccupied() + "|"
						+ room.getDescription());
			}
			System.out.println("Data saved successfully."); // confirming data save
		} catch (IOException e) {
			System.out.println("Error saving data: " + e.getMessage()); // handle file writing errors
		}
	}

	// Load room data from the file
	public void loadData() {
		// try with resources to automatically close the BufferedReader
		try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
			String line;
			// read each line from the file
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue; // skipping empty lines
				}

				// split the line into data field based on the delimiter '|'
				String[] data = line.split("\\|");

				if (data.length < 7) {
					System.out.println("Skipping invalid data format: " + line);
					continue; // skips the lines that dont have the expected number of fields
				}

				// Parse the room data from the file
				int roomID = !data[0].isEmpty() ? Integer.parseInt(data[0]) : 0;
				String roomName = data[1];
				String roomType = data[2];
				String roomCategory = data[3];
				double costPerDay = !data[4].isEmpty() ? Double.parseDouble(data[4]) : 0.0;
				boolean isOccupied = !data[5].isEmpty() && Boolean.parseBoolean(data[5]);
				String description = data.length > 6 ? data[6] : "";

				// creating the appropriate Room object based on the room category and type
				Room room = null;
				switch (roomCategory.toLowerCase()) {
				case "standard":
					// creating a specialStandardDouble or SpecialStandadrTriple based on roomType
					if ((roomID == 128 || roomID == 129) && roomType.equalsIgnoreCase("Double")) {
						room = new SpecialStandardDouble(roomName, description, costPerDay, 5, 2, 1);
					} else if (roomID == 130 && roomType.equalsIgnoreCase("Triple")) {
						room = new SpecialStandardTriple(roomName, description, costPerDay, 6, 3, 2);
					} else {
						// creating a regular StandardRoom
						room = new StandardRoom(roomName, description, costPerDay, roomType, roomCategory);
					}
					break;
				case "deluxe":
					// create a DeluxeRoom
					room = new DeluxeRoom(roomName, description, costPerDay, roomType);
					break;
				case "premium":
					// create a PremiumRoom
					room = new PremiumRoom(roomName, description, costPerDay, roomType);
					break;
				default:
					// handling invalid room categories in the data file
					throw new IllegalArgumentException("Invalid room category in data file: " + roomCategory);
				}

				// if the rooom is marked as occupied, check it in with a dummy guest ID
				if (isOccupied) {
					room.checkinRoom("Guest001", 1); // use a placeholder guest ID for loading
				}

				// add the room to the accommodations list
				accommodations.add(room);
			}
			System.out.println("Data loaded successfully."); // confirms that data loading was successful
		} catch (IOException | AccommodationException | IllegalArgumentException e) {
			// handle errors during file reading or data processing
			System.out.println("Error loading data: " + e.getMessage());
		}
	}
}
