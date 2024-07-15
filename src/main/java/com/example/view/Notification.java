package com.example.view;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Notification {
    private StackPane notificationPane;
    private Label notificationLabel;

    public Notification(StackPane root) {
        notificationPane = new StackPane();
        notificationPane.getStyleClass().add("notification-pane");
        notificationPane.setOpacity(0);

        notificationLabel = new Label();
        notificationLabel.getStyleClass().add("notification-label");

        notificationPane.getChildren().add(notificationLabel);
        root.getChildren().add(notificationPane);
    }

    public void showNotification(String message) {
        notificationLabel.setText(message);
        notificationPane.setOpacity(1);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), notificationPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    public static void ALERT(Alert.AlertType type, String title, String header, String content, Node node){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(node.getScene().getWindow());
        alert.showAndWait();
    }
}