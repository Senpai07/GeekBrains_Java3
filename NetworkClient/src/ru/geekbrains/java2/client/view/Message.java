package ru.geekbrains.java2.client.view;

import javafx.scene.control.Alert;

public class Message {
  public static void ShowMessage(String title, String message, Alert.AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
