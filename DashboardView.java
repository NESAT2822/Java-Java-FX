package com.hotelmanagement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DashboardView {
    private final Stage stage;
    private final List<Room> rooms;

    public DashboardView(Stage stage) {
        this.stage = stage;
        this.rooms = new ArrayList<>();
    }

    public void show() {
        VBox dashboardPane = new VBox(10);
        dashboardPane.setAlignment(Pos.CENTER);
        dashboardPane.setPadding(new Insets(25, 25, 25, 25));

        Label welcomeLabel = new Label("Welcome to the Hotel Management System");
        Button bookRoomButton = new Button("Book a Room");
        Button viewBookingsButton = new Button("View Bookings");

        dashboardPane.getChildren().addAll(welcomeLabel, bookRoomButton, viewBookingsButton);

        bookRoomButton.setOnAction(e -> showBookingView());
        viewBookingsButton.setOnAction(e -> showViewBookings());

        Scene scene = new Scene(dashboardPane, 600, 400);
        stage.setScene(scene);
    }

    private void showBookingView() {
        GridPane bookingPane = new GridPane();
        bookingPane.setAlignment(Pos.CENTER);
        bookingPane.setHgap(10);
        bookingPane.setVgap(10);
        bookingPane.setPadding(new Insets(25, 25, 25, 25));

        Label customerNameLabel = new Label("Customer Name:");
        TextField customerNameField = new TextField();
        Label roomNumberLabel = new Label("Room Number:");
        TextField roomNumberField = new TextField();
        Button bookRoomButton = new Button("Book Room");

        bookingPane.add(customerNameLabel, 0, 0);
        bookingPane.add(customerNameField, 1, 0);
        bookingPane.add(roomNumberLabel, 0, 1);
        bookingPane.add(roomNumberField, 1, 1);
        bookingPane.add(bookRoomButton, 1, 2);

        bookRoomButton.setOnAction(e -> {
            String customerName = customerNameField.getText();
            int roomNumber = Integer.parseInt(roomNumberField.getText());
            bookRoom(customerName, roomNumber);
        });

        Scene scene = new Scene(bookingPane, 400, 300);
        stage.setScene(scene);
    }

    private void showViewBookings() {
        VBox bookingsPane = new VBox(10);
        bookingsPane.setAlignment(Pos.CENTER);
        bookingsPane.setPadding(new Insets(25, 25, 25, 25));

        Label bookingsLabel = new Label("Current Bookings:");
        bookingsPane.getChildren().add(bookingsLabel);

        for (Room room : rooms) {
            if (room.isBooked()) {
                Label bookingLabel = new Label("Room " + room.getRoomNumber() + " is booked by " + room.getCustomerName());
                bookingsPane.getChildren().add(bookingLabel);
            }
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> show());
        bookingsPane.getChildren().add(backButton);

        Scene scene = new Scene(bookingsPane, 600, 400);
        stage.setScene(scene);
    }

    private void bookRoom(String customerName, int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (!room.isBooked()) {
                    room.book(customerName);
                    showAlert("Success", "Room " + roomNumber + " booked successfully!");
                    return;
                } else {
                    showAlert("Error", "Room " + roomNumber + " is already booked.");
                    return;
                }
            }
        }
        Room newRoom = new Room(roomNumber, "Standard");
        newRoom.book(customerName);
        rooms.add(newRoom);
        showAlert("Success", "Room " + roomNumber + " booked successfully!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
