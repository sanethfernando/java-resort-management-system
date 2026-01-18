package com.reservation.system;

// Represents a special standard double room that implements the SpecialServices interface.
public class SpecialStandardDouble extends StandardRoom implements SpecialServices {
    private int rampLength;
    private int rampWidth;
    private int emergencyCalls;

    // Constructor to initialize a SpecialStandardDouble room with special services.
    public SpecialStandardDouble(String roomName, String description, double costPerDay, int rampLength, int rampWidth, int emergencyCalls) {
        super(roomName, description, costPerDay, "Double", "Standard");
        this.rampLength = rampLength;
        this.rampWidth = rampWidth;
        this.emergencyCalls = emergencyCalls;
    }

    // getters and setters
    @Override
    public int getRampLength() {
        return rampLength;
    }

    @Override
    public int getRampWidth() {
        return rampWidth;
    }

    @Override
    public int getEmergencyCalls() {
        return emergencyCalls;
    }

    // Displays information about the room, including special services details.
    @Override
    public String displayRoomInfo() {
        return super.displayRoomInfo() + 
               "Special Services:\n" +
               "  Ramp Length  : " + rampLength + " meters\n" +
               "  Ramp Width   : " + rampWidth + " meters\n" +
               "  Emergency Calls: " + emergencyCalls + "\n";
    }
}
