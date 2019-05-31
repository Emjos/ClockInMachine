package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    AlertWindow alertWindow = new AlertWindow();
    Statement statement;
   static ServerConnect serverConnect = new ServerConnect();
public void clockInButton(){
    timeDate = new Date();
    System.out.println("Clock in " + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));
}

public void clockOutButton(){
    timeDate = new Date();
    System.out.println("Clock Out " + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));

}
@FXML
public void logInButton(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

    serverConnect.getConnection();
    String query = "{call 30712964_clock_in.look(?)}";
    CallableStatement statement = serverConnect.connection.prepareCall(query);
    statement.setString(1, emailText.getText());
    ResultSet rs = statement.executeQuery();
    String emailCheck = null;
    String passwordCheck = null ;
    boolean isAdmin = false;
    while (rs.next()) {
    emailCheck = rs.getString("user_email");
    passwordCheck = rs.getString("user_password");
    isAdmin = rs.getBoolean("user_is_admin");
    }
    if (emailText.getText().equals(emailCheck) && passwordText.getText().equals(passwordCheck) && isAdmin == true) {
        NewScene newScene = new NewScene();
        newScene.newScene(event, newScene.adminPane);
        alertWindow.setAlert("Zalogowano jako admin");

    } else if (emailText.getText().equals(emailCheck) && passwordText.getText().equals(passwordCheck) && isAdmin != true) {
        NewScene newScene = new NewScene();
        newScene.newScene(event,newScene.logInPane);
        alertWindow.setAlert("Zalogowano jako User");
    }
    else {
        alertWindow.setAlert("Bledne dane");
    }

}

@FXML
void registryLink(ActionEvent event) throws IOException {
    System.out.println("Rejestracja");
    NewScene newScene = new NewScene();
    newScene.newScene(event,newScene.registryPane);

}

}
