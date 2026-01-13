package com.hotelmanagement.controllers;

import com.hotelmanagement.models.Guest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuestController {

    private Stage stage;
    private ObservableList<Guest> guests;

    public GuestController(Stage stage) {
        this.stage = stage;
        this.guests = FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Guest Management");
        label.setStyle("-fx-font-size: 20px;");

        ListView<Guest> guestListView = new ListView<>(guests);
        guestListView.setPrefHeight(200);

        Button addGuestButton = new Button("Add Guest");
        addGuestButton.setOnAction(e -> openGuestForm(null));

        Button editGuestButton = new Button("Edit Guest");
        editGuestButton.setOnAction(e -> {
            Guest selectedGuest = guestListView.getSelectionModel().getSelectedItem();
            if (selectedGuest != null) {
                openGuestForm(selectedGuest);
            } else {
                showAlert("No Selection", "No Guest Selected", "Please select a guest in the list.");
            }
        });

        Button deleteGuestButton = new Button("Delete Guest");
        deleteGuestButton.setOnAction(e -> {
            Guest selectedGuest = guestListView.getSelectionModel().getSelectedItem();
            if (selectedGuest != null) {
                guests.remove(selectedGuest);
            } else {
                showAlert("No Selection", "No Guest Selected", "Please select a guest in the list.");
            }
        });

        HBox buttonsBox = new HBox(10, addGuestButton, editGuestButton, deleteGuestButton);
        vbox.getChildren().addAll(label, guestListView, buttonsBox);

        return vbox;
    }

    private void openGuestForm(Guest guest) {
        Stage formStage = new Stage();
        formStage.setTitle(guest == null ? "Add Guest" : "Edit Guest");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameField = new TextField();
        GridPane.setConstraints(nameField, 1, 0);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);
        TextField emailField = new TextField();
        GridPane.setConstraints(emailField, 1, 1);

        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 1, 2);
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (guest == null) {
                guests.add(new Guest(name, email));
            } else {
                guest.setName(name);
                guest.setEmail(email);
                guests.set(guests.indexOf(guest), guest);
            }
            formStage.close();
        });

        if (guest != null) {
            nameField.setText(guest.getName());
            emailField.setText(guest.getEmail());
        }

        grid.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, saveButton);
        formStage.setScene(new Scene(grid, 300, 200));
        formStage.show();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
