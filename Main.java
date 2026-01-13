package com.hotelmanagement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private final List<Room> rooms = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Management System");
        showLoginView(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showLoginView(Stage stage) {
        GridPane loginPane = new GridPane();
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginPane.add(usernameLabel, 0, 0);
        loginPane.add(usernameField, 1, 0);
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(passwordField, 1, 1);
        loginPane.add(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (isValidCredentials(username, password)) {
                showDashboardView(stage);
            } else {
                showAlert("Invalid Credentials", "Incorrect username or password.");
            }
        });

        Scene scene = new Scene(loginPane, 400, 300);
        stage.setScene(scene);
    }

    private boolean isValidCredentials(String username, String password) {
        // For simplicity, we use hardcoded credentials
        User user = new User("admin", "password");
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    private void showDashboardView(Stage stage) {
        VBox dashboardPane = new VBox(10);
        dashboardPane.setAlignment(Pos.CENTER);
        dashboardPane.setPadding(new Insets(25, 25, 25, 25));

        Label welcomeLabel = new Label("Welcome to the Hotel Management System");
        Button bookRoomButton = new Button("Book a Room");
        Button viewBookingsButton = new Button("View Bookings");

        dashboardPane.getChildren().addAll(welcomeLabel, bookRoomButton, viewBookingsButton);

        bookRoomButton.setOnAction(e -> showBookingView(stage));
        viewBookingsButton.setOnAction(e -> showViewBookings(stage));

        Scene scene = new Scene(dashboardPane, 600, 400);
        stage.setScene(scene);
    }

    private void showBookingView(Stage stage) {
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

    private void showViewBookings(Stage stage) {
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
        backButton.setOnAction(e -> showDashboardView(stage));
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
