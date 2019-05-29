package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistryPaneController {

    @FXML
    private TextField nameText;

    @FXML
    private TextField surnameText;

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private PasswordField repeatPasswordText;

    @FXML
    private Button registryButton;

    @FXML
    private Button backButton;
    @FXML
    private CheckBox adminButton;

@FXML
public void registryButtonInAction(ActionEvent even){

    System.out.println(nameText.getText());
    System.out.println(surnameText.getText());
    System.out.println(emailText.getText());
    System.out.println(passwordText.getText());
    System.out.println(repeatPasswordText.getText());
    System.out.println(adminButton.isSelected());
    AlertWindow allOk = new AlertWindow();
    if (passwordText.getText().equals(repeatPasswordText.getText())) {


        allOk.setAlert("Witaj " + nameText.getText() + " zarejestrowano Cie pomyslnie");
        backToMain(even);
    }
    else
        allOk.setAlert("Hasla nie sa takie same");

    }
    @FXML
    void BackButtonInActon(ActionEvent event) {
        backToMain(event);
    }

    public static void backToMain(ActionEvent event){

        NewScene newScene = null;
        try {
            newScene = new NewScene();
            newScene.newScene(event,newScene.mainPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


