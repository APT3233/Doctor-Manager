
package com.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            String username = hashString(usernameField.getText());
            String password = hashString(passwordField.getText());


            if (username.equals("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918") && password.equals("03c23bb0e235ce37ddfbb44823522a73fbc6716c05eaa9e42011f96ebee4ffc1")) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome! Login successful.");
                alert.showAndWait();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/view/Main.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Doctor Management System");
                } catch (Exception e) {
                    System.out.println("error to read Main file" +e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid login!");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
             
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private String hashString(String input){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(input.getBytes());
            String hashedString = bytesToHex(hashedBytes);

            return hashedString;

        }catch (NoSuchAlgorithmException e){
            return null;
        }
        
     }
     private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = String.format("%02x", b & 0xff);
            hexString.append(hex);
        }
        return hexString.toString();
    }

  }
