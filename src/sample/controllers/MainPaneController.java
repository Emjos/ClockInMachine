package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPaneController {

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button clockInButton;

    @FXML
    private Button clockOutButton;

    @FXML
    private Button logInButton;

    @FXML
    private Hyperlink registryLink;

    private SimpleDateFormat timeFormat= new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
    private Date timeDate;
    private NewScene newScene;


public void clockInButton(){
    timeDate = new Date();
    System.out.println("Clock in " + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));
}

public void clockOutButton(){
    timeDate = new Date();
    System.out.println("Clock Out " + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));

}

public void logInButton(){
    System.out.println("Email : " + emailText.getText());
    System.out.println("Password : " +passwordText.getText() );
    AlertWindow alertWindow = new AlertWindow();
    alertWindow.setAlert("Cos tam cos tam");

}


@FXML
void registryLink(ActionEvent event) {
    System.out.println("Rejestracja");

    try {

        NewScene newScene = new NewScene();
        newScene.newScene(event,newScene.registryPane);
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}
