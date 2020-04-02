package ru.geekbrains.java2.client.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.controller.ClientController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeNickName {
    @FXML
    public Button cancelButton;
    @FXML
    public Button changeNicknameButton;
    @FXML
    public TextField nicknameEdit;
    @FXML
    public Label currentNicknameLabel;

    private ClientController controller;

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void showWindow(String title) {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setMinWidth(300);
            FXMLLoader loaderChangeNickname = new FXMLLoader(getClass().getResource("../view/ChangeNickName.fxml"));
            Parent childChat = loaderChangeNickname.load();
            ChangeNickName changeNicknameController = loaderChangeNickname.getController();
            changeNicknameController.currentNicknameLabel.setText("Текущий ник: " + controller.getUsername());
            stage.setScene(new Scene(childChat));
            stage.showAndWait();
            stage.setOnCloseRequest(e -> stage.close());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeNicknameButtonAction() {
        String newNickname = nicknameEdit.getCharacters().toString();
        if (!newNickname.isBlank()) {
            controller.sendNewNickname(controller.getUsername(), newNickname);
            nicknameEdit.clear();
            Stage stage = (Stage) changeNicknameButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
