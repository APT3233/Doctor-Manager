package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.model.Doctor;
import com.example.model.DoctorList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
    private DoctorList list = new DoctorList();


    @FXML
    private Pane contentArea;

    @FXML
    private StackPane root;

    @FXML
    private Button addButton;

    @SuppressWarnings("unchecked")
    private TableView<Doctor> createTableView(ArrayList<Doctor> doctorList) {
        TableView<Doctor> tableView = new TableView<>();
    
        TableColumn<Doctor, String> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Doctor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Doctor, String> specialtyColumn = new TableColumn<>("Specialty");
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        TableColumn<Doctor, String> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAvai()));

        TableColumn<Doctor, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    
        tableView.getColumns().addAll(codeColumn, nameColumn, specialtyColumn, availabilityColumn, emailColumn);
        tableView.setItems(FXCollections.observableArrayList(doctorList));
        return tableView;
    }

    public static void showMainWindow() {
        try {
            Parent root = FXMLLoader.load(MainController.class.getResource("/com/example/view/Main.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Doctor Management System");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        ArrayList<TextField> textFields = new ArrayList<>();
        VBox vBox = new VBox(10);
        vBox.setId("vBox-add");

        String[] labels = { "Code", "Name", "Specialty", "Availability", "Email" };
        String[] prompts = { "Your code here", "abc", "Specialist Level 1", "1-10", "fun@hi.com" };

        Label titleAdd = new Label("Add Doctor");
        titleAdd.getStyleClass().add("title-add");
        vBox.getChildren().addAll(titleAdd);

        for (int i = 0; i < labels.length; i++) {
            Pair<HBox, TextField> pair = createLabelTextFieldPair(labels[i], prompts[i], 300, 45);
            vBox.getChildren().add(pair.getKey());
            textFields.add(pair.getValue());
        }

        Button addButton = new Button("Submit");
        addButton.getStyleClass().add("add-btn");
        addButton.setOnAction(e -> {
            String code = textFields.get(0).getText().trim();
            String name = textFields.get(1).getText().trim();
            String specialty = textFields.get(2).getText().trim();
            String availability = textFields.get(3).getText().trim();
            String email = textFields.get(4).getText().trim();

            try {
                
                if(list.addDoctor(code, name, specialty, availability, email))
                    com.example.view.Notification.ALERT(Alert.AlertType.INFORMATION, "ADD", "Add Doctor successfully",
                            "The doctor has been added successfully.", contentArea);
                else
                    com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "ADD", "Add Doctor failed",
                    "Doctors already exist", contentArea);
            }
            catch (Exception a) {
                com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "ADD", "Add Doctor failed",
                        "Failed to add the doctor. Please try again.", contentArea);
            }

            // try {
                
            //     if(list.addNew(new Doctor(code, name, specialty, availability, email)))
            //         com.example.view.Notification.ALERT(Alert.AlertType.INFORMATION, "ADD", "Add Doctor successfully",
            //                 "The doctor has been added successfully.", contentArea);
            //     else
            //         com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "ADD", "Add Doctor failed",
            //         "Doctors already exist", contentArea);
            // }
            // catch (Exception a) {
            //     com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "ADD", "Add Doctor failed",
            //             "Failed to add the doctor. Please try again.", contentArea);
            // }
        });

        vBox.getChildren().add(addButton);
        contentArea.getChildren().setAll(vBox);
    }

    private Pair<HBox, TextField> createLabelTextFieldPair(String label, String promptText, double width,
            double height) {
        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("label-add");
        TextField textField = new TextField();
        textField.setId("textField-featuer");
        textField.setPromptText(promptText);
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
        HBox hbox = new HBox(15, labelNode, textField);
        return new Pair<>(hbox, textField); // Return a Pair containing the HBox and the TextField
    }

    @FXML
    private void handleUpdate() {
        ArrayList<TextField> textFields = new ArrayList<>();
        VBox vBox = new VBox(10);
        vBox.setId("vBox-add");

        String[] labels = { "Old Code", "New Name", "New Specialty", "New Availability", "New Email" };
        String[] prompts = { "Old code to check", "abc", "Specialty", "avai/unavai", "thank@you.com" };

        Label titleAdd = new Label("Update Doctor");
        titleAdd.getStyleClass().add("title-upd");
        vBox.getChildren().addAll(titleAdd);

        for (int i = 0; i < labels.length; i++) {
            Pair<HBox, TextField> pair = createLabelTextFieldPair(labels[i], prompts[i], 300, 45);
            vBox.getChildren().add(pair.getKey());
            textFields.add(pair.getValue());
        }

        Button updDButton = new Button("Update");
        updDButton.getStyleClass().add("upd-btn");
        updDButton.setOnAction(e -> {
            String code = textFields.get(0).getText().trim();
            String name = textFields.get(1).getText().trim();
            String specialty = textFields.get(2).getText().trim();
            String availability = textFields.get(3).getText().trim();
            String email = textFields.get(4).getText().trim();

            try {
                if(list.updateDoctor(code, name, specialty, availability, email))
                    com.example.view.Notification.ALERT(Alert.AlertType.INFORMATION, "UPDATE", "Update Doctor successfully",
                            "The doctor has been updated successfully.", contentArea);
                else
                    com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "UPDATE", "Update Doctor failed",
                            "Failed to update the doctor. Please try again.", contentArea);
            }catch (Exception a) {
                System.out.print(a.getMessage());
                com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "UPDATE", "Update Doctor failed",
                        "Failed to update the doctor. Please try again.", contentArea);
            }

        });

        vBox.getChildren().add(updDButton);
        contentArea.getChildren().setAll(vBox);
    }


    
    @FXML
    private void handleSearch(ActionEvent event) {  
        contentArea.getChildren().clear();
        VBox vBox = new VBox(10);
        vBox.setId("vBox-ser");

        StackPane titlePane = new StackPane();
        titlePane.setAlignment(Pos.CENTER);
        Label titleSer = new Label("Search Doctor");
        titleSer.getStyleClass().add("title-ser");
        titlePane.getChildren().add(titleSer);
        vBox.getChildren().addAll(titlePane);

        Label labelNode = new Label("Code");
        labelNode.setId("label-ser");
        TextField textField = new TextField();
        textField.setId("textField-featuer");
        textField.setPromptText("Search Doctor by code");
        textField.setPrefWidth(300);
        textField.setPrefHeight(45);
        HBox hbox = new HBox(labelNode, textField);
        hbox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hbox);
        

        Button serButton = new Button("Search");
        serButton.getStyleClass().add("ser-btn");
        serButton.setOnAction(e -> {
            String code = textField.getText().trim();
            try{
                ArrayList<Doctor> newList = list.searchDoctorByCode(code);
                if(newList.isEmpty())
                    com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "SEARCH", "Search Doctor failed",
                        "Code not found!", contentArea);
                else{
                    VBox tableVBox = new VBox(); 
                    tableVBox.setId("vBox-table");
                    TableView<Doctor> tableView = createTableView(newList);
    
                    tableVBox.getChildren().add(tableView);
    
                    contentArea.getChildren().clear();
                    contentArea.getChildren().addAll(vBox, tableVBox);
                }
            }catch(Exception a){
                com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "SEARCH", "Search Doctor failed",
                        "Code not found!", contentArea);
            }
            
        });
        vBox.getChildren().add(serButton);
        contentArea.getChildren().add(vBox);
    }
    

    @FXML
    private void handleDelete() {   
        ArrayList<TextField> textFields = new ArrayList<>();
        VBox vBox = new VBox(10);
        vBox.setId("vBox-del");

        String[] labels = { "Code" };
        String[] prompts = { "Doctor code want to remove" };

        Label titleAdd = new Label("Delete Doctor");
        titleAdd.getStyleClass().add("title-del");
        vBox.getChildren().addAll(titleAdd);

        for (int i = 0; i < labels.length; i++) {
            Pair<HBox, TextField> pair = createLabelTextFieldPair(labels[i], prompts[i], 300, 45);
            vBox.getChildren().add(pair.getKey());
            textFields.add(pair.getValue());
        }

        Button delButton = new Button("Delete");
        delButton.getStyleClass().add("del-btn");
        delButton.setOnAction(e -> {
            String code = textFields.get(0).getText().trim();
            List<String> codes = Arrays.asList(code.split(","));
            codes = codes.stream().map(String::trim).collect(Collectors.toList());
            if (list.deleteDoctorsByCodes(codes)) {
                com.example.view.Notification.ALERT(Alert.AlertType.INFORMATION, "DELETE", "Deleted Doctor successfully",
                        "The doctor has been deleted successfully.", contentArea);
  
            } else {
                com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "DELETE", "Delete Doctor failed",
                        "Failed to delete the doctor. Please try again.", contentArea);
            }
        });

        vBox.getChildren().add(delButton);
        contentArea.getChildren().setAll(vBox);
    }

    @FXML
    private void handleShow() {
        contentArea.getChildren().clear();
        VBox vBox = new VBox(10);
        vBox.setId("vBox-show");

        StackPane titlePane = new StackPane();
        titlePane.setAlignment(Pos.CENTER);
        Label titleShow = new Label("Doctor List");
        titleShow.getStyleClass().add("title-show");
        titlePane.getChildren().add(titleShow);
        vBox.getChildren().addAll(titlePane);

        ArrayList<Doctor> newList = list.readDoctor();
        VBox tableVBox = new VBox(); 
        tableVBox.setId("vBox-table-show");
        TableView<Doctor> tableView = createTableView(newList);

        tableVBox.getChildren().add(tableView);

        contentArea.getChildren().clear();
        contentArea.getChildren().addAll(vBox, tableVBox);
        
    }

    @FXML
    private void handleFeedback() {
        ArrayList<TextField> textFields = new ArrayList<>();
        VBox vBox = new VBox(10);
        vBox.setId("vBox-feedback");

        Label titlefb = new Label("FeedBack ");
        titlefb.getStyleClass().add("title-fb");
        vBox.getChildren().addAll(titlefb);

        Pair<HBox, TextField> pair = createLabelTextFieldPair("NickName", "Your nickname or fullname", 300, 45);
        vBox.getChildren().add(pair.getKey());
        textFields.add(pair.getValue());


        TextArea content = new TextArea();
        content.setPromptText("Enter your feedback here...");
        content.setWrapText(true);
        content.setId("textArea");
        vBox.getChildren().addAll(content);

        Button fbButton = new Button("Submit");
        fbButton.getStyleClass().add("fb-btn");
        fbButton.setOnAction(e -> {
            String nickname = textFields.get(0).getText().trim();
            String text = content.getText();
            String message = "Name: " + nickname + "\n" + text;
            final String BOT_TOKEN = "7044275995:AAG4f4VbsjRyOwU5u49_CW5FXzOn3DUo2qw"; // you can change API & CHAT_ID
            final String CHAT_ID = "-4267368913";
            try {
                if(DoctorList.sendMessage(BOT_TOKEN, CHAT_ID,message))
                    com.example.view.Notification.ALERT(Alert.AlertType.CONFIRMATION, "Success", "Sumbit successfully !", "Thank you for feedback. See you again !!", contentArea);
                
                else
                    com.example.view.Notification.ALERT(Alert.AlertType.ERROR, "FAILED", "Sumbit failed !", "PLease try again !!", contentArea);

            } catch (Exception a) {
               a.printStackTrace();
            }

        });
        vBox.getChildren().add(fbButton);

        contentArea.getChildren().setAll(vBox);
    }
}
