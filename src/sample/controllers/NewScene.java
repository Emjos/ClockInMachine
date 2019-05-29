package sample.controllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class NewScene {
   public String registryLink = "../views/registryPane.fxml";
   public String mainLink = "../views/mainPane.fxml";
    Parent registryPane =  FXMLLoader.load(getClass().getResource(registryLink));
    Parent mainPane =  FXMLLoader.load(getClass().getResource(mainLink));

    public NewScene() throws IOException {
    }

    public void newScene(ActionEvent event, Parent paneParent) {

        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.hide();
        appStage.setScene(new Scene(paneParent));


        appStage.setResizable(false);
        appStage.show();
    }
}
