
package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminPaneController {

    @FXML
    private TableView<?> fullTable;

    @FXML
    private TableColumn<?, ?> emailTable;

    @FXML
    private TableColumn<?, ?> dateTable;

    @FXML
    private TableColumn<?, ?> clockInTable;

    @FXML
    private TableColumn<?, ?> clockOutTable;

    @FXML
    private Button isNullButton;

    @FXML
    private TextField dateInput;

    @FXML
    private Button dateCheckButton;

    @FXML
    private TextField emaiInput;

    @FXML
    private Button emailCheckButton;

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        NewScene newScene = new NewScene();
        newScene.newScene(event,newScene.mainPane);

    }

}




