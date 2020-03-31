package ru.geekbrains.java2.client.view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.controller.ClientController;

import java.util.List;

public class ClientChat {
  @FXML public ListView<String> usersList;
  @FXML public TextField userMessageEdit;
  @FXML public Button sendButton;
  @FXML public ListView<Object> chatMessageList;
  @FXML public Button exitButton;
  @FXML public Button clearButton;
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

  public void initialize() {
    clearButton.setTooltip(new Tooltip("Очистить чат"));
    sendButton.setTooltip(new Tooltip("Отправить сообщение"));
    exitButton.setTooltip(new Tooltip("Закрыть программу"));
  }

  @FXML
  public void closeProgram() {
    // get a handle to the stage
    Stage stage = (Stage) exitButton.getScene().getWindow();
    // do what you have to do
    stage.close();
    System.exit(0);
  }

  @FXML
  public void sendMessage() {

    String newMessage = userMessageEdit.getCharacters().toString();
    if (!newMessage.isBlank()) {

      appendOwnMessage(newMessage);

      if (usersList.getSelectionModel().getSelectedIndex() < 1) {
        controller.sendMessageToAllUsers(newMessage);
      } else {
        String username = usersList.getSelectionModel().getSelectedItem();
        controller.sendPrivateMessage(username, newMessage);
      }

      userMessageEdit.clear();
    }
  }

  private void appendOwnMessage(String newMessage) {
    Text myText = new Text(String.format("%s: %s", "Я", newMessage));
    myText.setStyle("-fx-font-weight: bold");
    chatMessageList.getItems().addAll(myText);
  }

  @FXML
  public void clearChat() {
    chatMessageList.getItems().clear();
  }

  public void appendMessage(String incomeMessage) {
    Platform.runLater(() -> chatMessageList.getItems().addAll(incomeMessage));
  }

  public void updateUsers(List<String> users) {
    Platform.runLater(
        () -> {
          usersList.getItems().clear();
          usersList.getItems().addAll(users);
        });
  }

  public void showError(String errorMessage) {
    Message.ShowMessage("Error", errorMessage, Alert.AlertType.ERROR);
  }
}
