package ru.geekbrains.java2.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.controller.ClientController;

import java.io.IOException;

public class NetworkClient extends Application {

  public static final String DEFAULT_HOST = "localhost";
  public static final int DEFAULT_PORT = 8189;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      ClientController clientController =
          new ClientController(DEFAULT_HOST, DEFAULT_PORT, primaryStage);
      clientController.runApplication();
    } catch (IOException e) {
      System.err.println("Failed to connect to server! Please, check you network settings");
    }
  }
}
