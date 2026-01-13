package com.hotelmanagement.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController {
    private Stage stage;

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public GridPane getView() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        grid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        return grid;
    }

    private void handleLogin(String username, String password) {
        if (username.equals("admin") && password.equals("password")) {
            DashboardController dashboardController = new DashboardController(stage);
            stage.setScene(new Scene(dashboardController.getView(), 600, 400));
            stage.setTitle("Hotel Management System - Dashboard");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password!");
            alert.showAndWait();
        }
    }
}
