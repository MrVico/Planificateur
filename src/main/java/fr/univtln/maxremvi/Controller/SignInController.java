package fr.univtln.maxremvi.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.event.ActionEvent;

public class SignInController {

    @FXML
    public void handleSignInButtonClick(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Does this actually work?");
        alert.showAndWait();
    }

    @FXML
    public void handleRegisterButtonClick(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Doesn't seem to! =(");
        alert.showAndWait();
    }
}
