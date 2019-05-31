
        package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

        public class AdminPaneController {

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        NewScene newScene = new NewScene();
        newScene.newScene(event,newScene.mainPane);

    }

}
