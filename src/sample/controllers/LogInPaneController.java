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
import java.nio.channels.CancelledKeyException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInPaneController implements Initializable {

    ServerConnect serverConnect = new ServerConnect();

    @FXML
    private TableView<Clocks> full_table;

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
    private ObservableList<Clocks> observableList;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public void initialize() {  // Auto do when open




    }

    @FXML
    void backButtonAction(ActionEvent event) throws IOException, IOException {
        NewScene newScene = new NewScene();
        newScene.newScene(event, newScene.mainPane);
    }


    public void set_title(User user){
        infoField.setText("Witaj " +user.getName() + " " + user.getSurname() + "                    Adres Email = " + user.getEmail());
    }

    public void update_list(String email) throws SQLException, ClassNotFoundException {
       date_table.setCellValueFactory(new PropertyValueFactory<>("date"));
       clock_in_table.setCellValueFactory(new PropertyValueFactory<>("clockIn"));
       clock_out_table.setCellValueFactory(new PropertyValueFactory<>("clockOut"));
        observableList = FXCollections.observableArrayList();

        String query = "select * from `30712964_clock_in`.clocks\n" +
                "where username ='"+ email +"'";
        System.out.println(query);
        serverConnect.getConnection();
        PreparedStatement statement = serverConnect.connection.prepareCall(query);
        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            System.out.println(rs.getString("date"));
        observableList.add(new Clocks(rs.getString("date"),rs.getString("clock_in"),rs.getString("clock_out")));
//            date_table.setText(rs.getString("date"));
//            clock_in_table.setText(rs.getString("clock_in"));
//            clock_out_table.setText(rs.getString("clock_out"));
        }

        full_table.setItems(observableList);


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

