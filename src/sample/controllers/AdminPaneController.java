
package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPaneController {

    @FXML
    private TableView<Clocks> fullTable;

    @FXML
    private TableColumn<Clocks, String> emailTable;

    @FXML
    private TableColumn<Clocks, String> dateTable;

    @FXML
    private TableColumn<Clocks, String> clockInTable;

    @FXML
    private TableColumn<Clocks, String> clockOutTable;

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

    ServerConnect serverConnect = new ServerConnect();

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        NewScene newScene = new NewScene();
        newScene.newScene(event,newScene.mainPane);

    }


    @FXML
    void isNullButton(ActionEvent event) throws SQLException, ClassNotFoundException {
        String query = "select * from  `30712964_clock_in`.clocks where clock_out is null or clock_in is null";

        setupList(query, emailTable, dateTable, clockInTable, clockOutTable, serverConnect, fullTable);
        System.out.println("Tu jestem 1");
    }

    private void update_list(String query) throws SQLException, ClassNotFoundException {


        //setupList(query, emailTable, dateTable, clockInTable, clockOutTable, serverConnect, fullTable);


    }

    static void setupList(String query, TableColumn<Clocks, String> emailTable, TableColumn<Clocks, String> dateTable, TableColumn<Clocks, String> clockInTable, TableColumn<Clocks, String> clockOutTable, ServerConnect serverConnect, TableView<Clocks> fullTable) throws SQLException, ClassNotFoundException {

        emailTable.setCellValueFactory(new PropertyValueFactory<>("email"));

        dateTable.setCellValueFactory(new PropertyValueFactory<>("date"));

        clockInTable.setCellValueFactory(new PropertyValueFactory<>("clockIn"));
        clockOutTable.setCellValueFactory(new PropertyValueFactory<>("clockOut"));
        ObservableList<Clocks> observableList = FXCollections.observableArrayList();
        System.out.println("Tu jestem 2");
        serverConnect.getConnection();
        System.out.println("Tu jestem 3");
        PreparedStatement statement = serverConnect.connection.prepareCall(query);
        System.out.println("Tu jestem 4");
        ResultSet rs = statement.executeQuery();
        System.out.println("Tu jestem 5");

        while (rs.next()){

            observableList.add(new Clocks(rs.getString("username"),rs.getString("date"),rs.getString("clock_in"),rs.getString("clock_out")));        }

        fullTable.setItems(observableList);
    }
}






