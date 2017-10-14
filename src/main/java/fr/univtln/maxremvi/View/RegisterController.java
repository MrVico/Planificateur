package fr.univtln.maxremvi.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField firstname;

    @FXML
    void handleButtonRegisterClick(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(this.firstname.getText());
        alert.showAndWait();
    }

    public boolean signUp(){
        System.out.println(firstname);
        return false;
    }
}
