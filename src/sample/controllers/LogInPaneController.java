package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInPaneController implements Initializable {

    ServerConnect serverConnect = new ServerConnect();

    @FXML
    private TableView<Clocks> full_table;
    @FXML
    private TableColumn<Clocks, String> email_table;
    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Clocks, String> date_table;

    @FXML
    private TableColumn<Clocks, String> clock_in_table;

    @FXML
    private TableColumn<Clocks, String> clock_out_table;
    @FXML
    private TextField infoField;
    private  User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



   // public void initialize() {  // Auto do when open}



    @FXML
    void backButtonAction(ActionEvent event) throws  IOException {
        NewScene newScene = new NewScene();
        newScene.newScene(event, newScene.mainPane);
    }


    public void set_title(User user){
        infoField.setText("Witaj " +user.getName() + " " + user.getSurname() + "                    Adres Email = " + user.getEmail());
    }

    public void update_list(String email) throws SQLException, ClassNotFoundException {
        String query = "select * from `30712964_clock_in`.clocks\n" +
                "where username ='"+ email +"'";

        AdminPaneController.setupList(query, email_table, date_table, clock_in_table, clock_out_table, serverConnect, full_table);


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

