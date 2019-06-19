package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controllers.FxScenes.AlertWindow;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private static ServerConnect serverConnect = new ServerConnect();
    AlertWindow allOk = new AlertWindow();

@FXML
public void registryButtonInAction(ActionEvent even) throws SQLException, ClassNotFoundException {
    serverConnect.getConnection();

    String query = "{call 30712964_clock_in.look(?)}";
    CallableStatement statement = serverConnect.connection.prepareCall(query);
    statement.setString(1, emailText.getText());
    ResultSet rs = statement.executeQuery();
    int check = 0;
    while (rs.next()) {
        check++;

    }
    if (check != 0) {
        allOk.setAlert("Uzytkownik juz istnieje");
    } else {
        if (passwordText.getText().equals(repeatPasswordText.getText())) {
            String procedue = "{call 30712964_clock_in.add_new(?,?,?,?,?)}";
            PreparedStatement pstmt = serverConnect.connection.prepareStatement(procedue);
            pstmt.setString(1, nameText.getText());
            pstmt.setString(2, surnameText.getText());
            pstmt.setString(3, emailText.getText().toLowerCase());
            pstmt.setString(4, passwordText.getText());
            pstmt.setBoolean(5, adminButton.isSelected());
            pstmt.executeUpdate();

            allOk.setAlert("Witaj " + nameText.getText() + " Zarejestrowano Cie pomyslnie \n" +
                    "Imie : " + nameText.getText() + " \n" +
                    "Nazwisko : " + surnameText.getText() + " \n" +
                    "Email : " + emailText.getText());

                    backToMain(even);

        } else
            allOk.setAlert("Hasla nie sa takie same");

    }


    //------------------
    System.out.println(nameText.getText());
    System.out.println(surnameText.getText());
    System.out.println(emailText.getText());
    System.out.println(passwordText.getText());
    System.out.println(repeatPasswordText.getText());
    System.out.println(adminButton.isSelected());
    //----------------------

}
    @FXML
    void BackButtonInActon(ActionEvent event) {
        backToMain(event);
    }

    public static void backToMain(ActionEvent event){

        NewScene newScene;
        try {
            newScene = new NewScene();
            newScene.newScene(event,newScene.mainPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


