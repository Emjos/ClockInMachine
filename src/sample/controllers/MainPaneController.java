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
import java.util.function.ToDoubleBiFunction;

public class MainPaneController {

    private static AlertWindow alertWindow = new AlertWindow();
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
    static String emailCheck = null;
    static String passwordCheck = null ;
    static boolean isAdmin = false;
    static String userName = null;
    static String userSurname = null;
    Statement statement;
   static ServerConnect serverConnect = new ServerConnect();
public void clockInButton() throws SQLException, IOException, ClassNotFoundException {
    timeDate = new Date();

    int whatToDo = checkData(emailText.getText(),passwordText.getText());
    if(whatToDo == 0){
        alertWindow.setAlert("Bledne dane");
    }
    else if(whatToDo == 1){
        String nameplusSurname = userName +" " + userSurname;
        alertWindow.setAlert("\t" +nameplusSurname  + "\n\tJestes Adminem!!\n\tNie musisz sie logowac");
    }

    else if (whatToDo == 2){
        System.out.println("Clock in " + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));
        String query = "insert into clocks(username, date, clock_in) value ("+emailCheck+","+dateFormat.format(timeDate) +","+timeFormat.format(timeDate)+")";
        System.out.println(query);
        // TODO dodwanie do wspolniej tablicy clock clock in, sprawdzanie czy clock in juz nie istnieje
    }
}

public void clockOutButton(){
    timeDate = new Date();
    System.out.println("Clock Out " + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));

}
@FXML
public void logInButton(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

int whatToDo = checkData(emailText.getText(),passwordText.getText());

if (whatToDo == 0){  alertWindow.setAlert("Bledne dane");}
else if (whatToDo ==1){
    NewScene newScene = new NewScene();
    newScene.newScene(event, newScene.adminPane);
    alertWindow.setAlert("Zalogowano jako admin");}
else if(whatToDo == 2){  NewScene newScene = new NewScene();
    newScene.newScene(event,newScene.logInPane);
    alertWindow.setAlert("Zalogowano jako User");}
else
    alertWindow.setAlert("COS POSZLO NIE TAK");
}

@FXML
void registryLink(ActionEvent event) throws IOException {
    System.out.println("Rejestracja");
    NewScene newScene = new NewScene();
    newScene.newScene(event,newScene.registryPane);

}


public static int checkData(String username,String password) throws IOException, SQLException, ClassNotFoundException {

    int result;
    serverConnect.getConnection();
    String query = "{call 30712964_clock_in.look(?)}";
    CallableStatement statement = serverConnect.connection.prepareCall(query);
    statement.setString(1, username);
    ResultSet rs = statement.executeQuery();

    while (rs.next()) {
        userName = rs.getString("user_name");
        userSurname = rs.getString("user_surname");
        emailCheck = rs.getString("user_email");
        passwordCheck = rs.getString("user_password");
        isAdmin = rs.getBoolean("user_is_admin");

    }

    if (username.equals(emailCheck) && password.equals(passwordCheck) && isAdmin == true) {

            result = 1;
    } else if (username.equals(emailCheck) && password.equals(passwordCheck) && isAdmin != true) {

        result = 2;
    }
    else {

        result = 0;
    }
    return result;
}

}
