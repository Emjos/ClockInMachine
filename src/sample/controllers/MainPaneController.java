package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.controllers.Classes.User;
import sample.controllers.FxScenes.AlertWindow;


import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateFormat;
    private Date timeDate;
    private static String emailCheck = null;
    private static String passwordCheck = null ;
    private static boolean isAdmin = false;
    private static String userName = null;
    private static String userSurname = null;



   private static ServerConnect serverConnect = new ServerConnect();

    public MainPaneController() {
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }


    public void clockInButton() throws SQLException, ClassNotFoundException {
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
        System.out.println("Clock in "+ emailText.getText() + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));

        clockChcecker();
        ResultSet rsClock = clockChcecker();

        int isExistOut = 0;


        while (rsClock.next()){
            String isNullCheck = rsClock.getString(3);

           if (isNullCheck == null){
               String queryForNull = "{call 30712964_clock_in.upgrade_clock_in(?,?,?)}";
               ClockInOut(queryForNull);
               alertWindow.setAlert("Zalogowano");
               isExistOut = 2;
               break;
           }
            isExistOut = 1;


        }

        if (isExistOut == 1){
            alertWindow.setAlert("Clock in juz istnieje");

        }
        else if (isExistOut == 0) {
            String procedue = "{call 30712964_clock_in.add_clock_in(?,?,?)}";
            ClockInOut(procedue);
            alertWindow.setAlert("Zalogowano");

        }


    }
}

    private ResultSet clockChcecker() throws SQLException {
        String query = "{call 30712964_clock_in.check_clock(?,?)}";
        CallableStatement statement = serverConnect.connection.prepareCall(query);
        statement.setString(1,emailText.getText());
        statement.setString(2,dateFormat.format(timeDate));
        return statement.executeQuery();
    }

    public void clockOutButton() throws SQLException, ClassNotFoundException {
    timeDate = new Date();
    System.out.println("Clock Out " + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));
    int whatToDo = checkData(emailText.getText(),passwordText.getText());
    if(whatToDo == 0){
        alertWindow.setAlert("Bledne dane");
    }
    else if(whatToDo == 1){
        String nameplusSurname = userName +" " + userSurname;
        alertWindow.setAlert("\t" +nameplusSurname  + "\n\tJestes Adminem!!\n\tNie musisz sie wylogowywac");
    }

    else if (whatToDo == 2){
        System.out.println("Clock out "+ emailText.getText() + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));
        clockChcecker();
        ResultSet rsClock = clockChcecker();
        int isExist = 0;


        while (rsClock.next()){


            String isNullCheck = rsClock.getString(4);

            if (isNullCheck == null){

                String queryForNull = "{call 30712964_clock_in.upgrade_clock_out(?,?,?)}";

                ClockInOut(queryForNull);
                alertWindow.setAlert("Wylogowano");
                isExist = 2;
                break;

            }
            isExist =1;


        }

        if (isExist ==  1){
            alertWindow.setAlert("Clock OUT juz istnieje");
        }
        else if (isExist ==0) {
            String procedue = "{call 30712964_clock_in.add_clock_out(?,?,?)}";
             ClockInOut(procedue);
            alertWindow.setAlert("Wylogowano");

        }




    }
}
    private void ClockInOut(String procedue) throws SQLException {
        PreparedStatement pstmt = serverConnect.connection.prepareStatement(procedue);
        pstmt.setString(1,emailText.getText());
        pstmt.setString(2,dateFormat.format(timeDate));
        pstmt.setString(3,timeFormat.format(timeDate));

        pstmt.executeUpdate();
    }

    @FXML
public void logInButton(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

int whatToDo = checkData(emailText.getText(),passwordText.getText());

if (whatToDo == 0){  alertWindow.setAlert("Bledne dane");}
else if (whatToDo ==1){
    NewScene newScene = new NewScene();
    newScene.newScene(event, newScene.adminPane);
    alertWindow.setAlert("Zalogowano jako admin");}



else if(whatToDo == 2){
    User user = new User(userName,userSurname,emailCheck,isAdmin);
    Stage stage = (Stage) logInButton.getScene().getWindow();
    stage.close();
    Platform.setImplicitExit(true);
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/LoginPane.fxml"));
    Parent root1 = fxmlLoader.load();
    Stage stage1 = new Stage();
    stage1.setTitle("");
    stage1.setResizable(false);
    stage1.setScene(new Scene(root1));

    LogInPaneController controller = fxmlLoader.getController();
    controller.set_title(user);
    controller.update_list(user.getEmail());
    stage1.show();



    alertWindow.setAlert("Zalogowano jako User");
}
else
    alertWindow.setAlert("COS POSZLO NIE TAK");
}

@FXML
void registryLink(ActionEvent event) throws IOException {
    System.out.println("Rejestracja");
    NewScene newScene = new NewScene();
    newScene.newScene(event,newScene.registryPane);

}


private static int checkData(String username, String password) throws SQLException, ClassNotFoundException {

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

    if (username.equals(emailCheck) && password.equals(passwordCheck) && isAdmin) {

            result = 1;
    } else if (username.equals(emailCheck) && password.equals(passwordCheck) && !isAdmin) {

        result = 2;
    }
    else {

        result = 0;
    }
    return result;
}

}
