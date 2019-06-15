package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;


import javax.imageio.IIOParam;
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

    private SimpleDateFormat timeFormat= new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private Date timeDate;
    static String emailCheck = null;
    static String passwordCheck = null ;
    static boolean isAdmin = false;
    static String userName = null;
    static String userSurname = null;



   private static ServerConnect serverConnect = new ServerConnect();


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
        System.out.println("Clock in "+ emailText.getText() + timeFormat.format(timeDate) + " " + dateFormat.format(timeDate));

        clockChcecker();
        ResultSet rsClock = clockChcecker();

        boolean isExistOut = false;


        while (rsClock.next()){
            String isNullCheck = rsClock.getString(3);

           if (isNullCheck == null){
               String queryForNull = "{call 30712964_clock_in.upgrade_clock_in(?,?,?)}";
               ClockInOut(queryForNull);
           }
            isExistOut = true;


        }

        if (isExistOut == true){
            alertWindow.setAlert("Clock in juz istnieje");

        }
        else if (isExistOut == false) {
            String procedue = "{call 30712964_clock_in.add_clock_in(?,?,?)}";
            ClockInOut(procedue);

        }


    }
}

    private ResultSet clockChcecker() throws SQLException {
        String query = "{call 30712964_clock_in.check_clock(?,?)}";
        CallableStatement statement = serverConnect.connection.prepareCall(query);
        statement.setString(1,emailText.getText());
        statement.setString(2,dateFormat.format(timeDate));
        ResultSet rsClock =statement.executeQuery();
        return rsClock;
    }

    public void clockOutButton() throws SQLException, IOException, ClassNotFoundException {
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
        boolean isExist = false;


        while (rsClock.next()){


            String isNullCheck = rsClock.getString(4);

            if (isNullCheck == null){

                String queryForNull = "{call 30712964_clock_in.upgrade_clock_out(?,?,?)}";

                ClockInOut(queryForNull);
            }
            isExist = true;


        }

        if (isExist){
            alertWindow.setAlert("Clock OUT juz istnieje");
        }
        else if (!isExist) {
            String procedue = "{call 30712964_clock_in.add_clock_out(?,?,?)}";
             ClockInOut(procedue);

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

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/LoginPane.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setTitle("");
    stage.setScene(new Scene(root1));

    LogInPaneController controller = fxmlLoader.<LogInPaneController>getController();
    controller.set_title(user);
    controller.update_list(user.getEmail());
    stage.show();



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
