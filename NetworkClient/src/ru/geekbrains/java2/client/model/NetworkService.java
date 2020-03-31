package ru.geekbrains.java2.client.model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.command.AuthCommand;
import ru.geekbrains.java2.client.command.ErrorCommand;
import ru.geekbrains.java2.client.command.MessageCommand;
import ru.geekbrains.java2.client.command.UpdateUsersListCommand;
import ru.geekbrains.java2.client.controller.AuthEvent;
import ru.geekbrains.java2.client.controller.ClientController;
import ru.geekbrains.java2.client.controller.MessageHandler;
import ru.geekbrains.java2.client.view.Message;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class NetworkService {

  private final String host;
  private final int port;
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  private ClientController controller;

  private MessageHandler messageHandler;
  private AuthEvent successfulAuthEvent;
  private String nickname;

  public NetworkService(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void connect(ClientController controller) throws IOException {
    this.controller = controller;
    socket = new Socket(host, port);
    in = new ObjectInputStream(socket.getInputStream());
    out = new ObjectOutputStream(socket.getOutputStream());
    runReadThread();
  }

  private void runReadThread() {
    new Thread(
            () -> {
              while (true) {
                try {
                  Command command = (Command) in.readObject();
                  switch (command.getType()) {
                    case AUTH -> {
                      AuthCommand commandData = (AuthCommand) command.getData();
                      nickname = commandData.getUsername();
                      successfulAuthEvent.authIsSuccessful(nickname);
                    }
                    case MESSAGE -> {
                      MessageCommand commandData = (MessageCommand) command.getData();
                      if (messageHandler != null) {
                        String message = commandData.getMessage();
                        String username = commandData.getUsername();
                        if (username != null) {
                          message = username + ": " + message;
                        }
                        messageHandler.handle(message);
                      }
                    }
                    case AUTH_ERROR, ERROR -> {
                      ErrorCommand commandData = (ErrorCommand) command.getData();
                      controller.showErrorMessage(commandData.getErrorMessage());
                    }
                    case UPDATE_USERS_LIST -> {
                      UpdateUsersListCommand commandData =
                              (UpdateUsersListCommand) command.getData();
                      List<String> users = commandData.getUsers();
                      Platform.runLater(() -> controller.updateUsersList(users));
                    }
                    default -> System.err.println("Unknown type of command: " + command.getType());
                  }

//                  if (message.startsWith("/auth")) {
//                    String[] messageParts = message.split("\\s+", 2);
//                    nickname = messageParts[1];
//                  } else if (messageHandler != null) {
//                    messageHandler.handle(message);
//                  } else
//                    Platform.runLater(
//                        () ->
//                            Message.ShowMessage(
//                                "Ошибка аутентификации!", message, Alert.AlertType.ERROR));
                } catch (IOException e) {
                  System.out.println("Поток чтения был прерван!");
                  return;
                } catch (ClassNotFoundException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
  }

  public void sendCommand(Command command) throws IOException {
    out.writeObject(command);
  }

  public void setMessageHandler(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  public void setSuccessfulAuthEvent(AuthEvent successfulAuthEvent) {
    this.successfulAuthEvent = successfulAuthEvent;
  }

  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
