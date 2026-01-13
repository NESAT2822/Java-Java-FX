package com.hotelmanagement;

public class Room {
    private final int roomNumber;
    private final String type;
    private boolean isBooked;
    private String customerName;

    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isBooked = false;
        this.customerName = null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void book(String customerName) {
        this.isBooked = true;
        this.customerName = customerName;
    }

    public void unbook() {
        this.isBooked = false;
        this.customerName = null;
    }
}
