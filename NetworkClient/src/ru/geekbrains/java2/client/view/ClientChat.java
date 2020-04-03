package ru.geekbrains.java2.client.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.controller.ClientController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ClientChat {
    public static final int MAX_HISTORY_SIZE = 100;
    public static final String NAME_MESS_ARH = "msg_%s.arh";
    @FXML
    public ListView<String> usersList;
    @FXML
    public TextField userMessageEdit;
    @FXML
    public Button sendButton;
    @FXML
    public ListView<Object> chatMessageList;
    @FXML
    public Button exitButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button changeNicknameButton;
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
        saveMessagesHistory();
        stage.close();
        controller.shutdown();
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

    public void saveMessagesHistory() {
        String fileName = String.format(NAME_MESS_ARH, controller.getUsername());
        List<String> lines = new ArrayList<>();
        for (Object chatMessageListItem : chatMessageList.getItems()) {
            if (chatMessageListItem instanceof Text) {
                Text text = (Text) chatMessageListItem;
                lines.add(text.getText());
            } else if (chatMessageListItem instanceof String) {
                lines.add((String) chatMessageListItem);
            }
        }
        try {
            Files.deleteIfExists(Paths.get(fileName));
            Files.write(Paths.get(fileName), lines, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void changeNicknameButtonClick() {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Change nickname");
            stage.setMinWidth(300);
            FXMLLoader loaderChangeNickname = new FXMLLoader(getClass().getResource("../view/ChangeNickName.fxml"));
            Parent childChat = loaderChangeNickname.load();
            ChangeNickName changeNicknameController = loaderChangeNickname.getController();
            changeNicknameController.setController(controller);
            changeNicknameController.currentNicknameLabel.setText("Текущий ник: " + controller.getUsername());
            stage.setScene(new Scene(childChat));
            stage.showAndWait();
            stage.setOnCloseRequest(e -> stage.close());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMessagesHistory() {
        String fileName = String.format(NAME_MESS_ARH, controller.getUsername());
        List<String> lines;

        try {
            if (Files.exists(Paths.get(fileName))) {
                lines = Files.readAllLines(Paths.get(fileName));
                int size = lines.size();
                if (size > MAX_HISTORY_SIZE) {
                    lines.subList(0, size - MAX_HISTORY_SIZE).clear();
                }
                chatMessageList.getItems().addAll(lines);
                Files.deleteIfExists(Paths.get(fileName));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
