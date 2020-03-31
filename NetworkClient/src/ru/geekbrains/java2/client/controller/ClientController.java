package ru.geekbrains.java2.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.model.NetworkService;
import ru.geekbrains.java2.client.view.AuthDialog;
import ru.geekbrains.java2.client.view.ClientChat;

import java.io.IOException;
import java.util.List;

import static ru.geekbrains.java2.client.Command.*;

public class ClientController {

  private final NetworkService networkService;
  private String nickname;
  private Stage primaryStage;
  private Parent rootChat;
  private ClientChat clientChat;
  private AuthDialog authDialog;

  public ClientController(String serverHost, int serverPort, Stage primaryStage) {
    this.networkService = new NetworkService(serverHost, serverPort);
    this.primaryStage = primaryStage;
  }

  public void runApplication() throws IOException {
    openAuth();
    connectToServer();
    runAuthProcess();
  }

  private void openAuth() {
    FXMLLoader loaderAuth = new FXMLLoader();
    try {
      rootChat = loaderAuth.load(getClass().getResourceAsStream("../view/AuthDialog.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    authDialog = loaderAuth.getController();
    authDialog.setActive(true);
    authDialog.setController(this);

    primaryStage.setTitle("Chat client 1.0");
    primaryStage.setScene(new Scene(rootChat));
    primaryStage.setIconified(false);
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> System.exit(0));
  }

  private void runAuthProcess() {
    networkService.setSuccessfulAuthEvent(
        nickname -> {
          setUserName(nickname);
          Platform.runLater(this::openChat);
        });
    //        authDialog.setVisible(true);

  }

  private void openChat() {
    FXMLLoader loaderChat = new FXMLLoader();
    try {
      rootChat = loaderChat.load(getClass().getResourceAsStream("../view/ClientChat.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    authDialog.setActive(false);
    clientChat = loaderChat.getController();
    clientChat.setActive(true);
    clientChat.setController(this);

    primaryStage.setTitle("Chat client 1.0 : " + nickname);
    primaryStage.setScene(new Scene(rootChat));
    primaryStage.setIconified(false);
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    networkService.setMessageHandler(clientChat::appendMessage);
  }

  private void setUserName(String nickname) {
    this.nickname = nickname;
  }

  private void connectToServer() throws IOException {
    try {
      networkService.connect(this);
    } catch (IOException e) {
      System.err.println("Failed to establish server connection");
      throw e;
    }
  }

  public void sendAuthMessage(String login, String pass) throws IOException {
    networkService.sendCommand(authCommand(login, pass));
  }

  public void sendMessageToAllUsers(String message) {
    try {
      networkService.sendCommand(broadcastMessageCommand(message));
    } catch (IOException e) {
      clientChat.showError("Failed to send message!");
      e.printStackTrace();
    }
  }

  public void shutdown() {
    networkService.close();
  }

  public String getUsername() {
    return nickname;
  }

  public void updateUsersList(List<String> users) {
    users.remove(nickname);
    users.add(0, "All");
    clientChat.updateUsers(users);
  }

  public void sendPrivateMessage(String username, String message) {
    try {
      networkService.sendCommand(privateMessageCommand(username, message));
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
  }

  public void showErrorMessage(String errorMessage) {
    if (authDialog.isActive()) {
      authDialog.showError(errorMessage);
    } else if (clientChat.isActive()) {
      clientChat.showError(errorMessage);
    }
  }
}
