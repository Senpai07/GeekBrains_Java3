package ru.geekbrains.java2.client.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.controller.ClientController;

import java.io.IOException;

public class AuthDialog {
  @FXML public Button exitButton;
  @FXML public TextField userNameEdit;
  @FXML public Button authButton;
  @FXML public TextField userPassEdit;
  private boolean active = false;

  private ClientController controller;

  public void setController(ClientController controller) {
    this.controller = controller;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }

  @FXML
  public void closeProgram(ActionEvent actionEvent) {
    // get a handle to the stage
    Stage stage = (Stage) exitButton.getScene().getWindow();
    // do what you have to do
    stage.close();
    System.exit(0);
  }

  @FXML
  public void authButtonAction(ActionEvent actionEvent) {
    try {
      controller.sendAuthMessage(userNameEdit.getText().trim(), userPassEdit.getText().trim());
    } catch (IOException e) {
      showError("Ошибка при попытке аутентификации!");
    }
  }

  public void showError(String errorMessage) {
    Platform.runLater(() -> Message.ShowMessage("Error", errorMessage, Alert.AlertType.ERROR));
  }
}
