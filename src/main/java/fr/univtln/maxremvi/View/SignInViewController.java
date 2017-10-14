package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Controller.App;
import fr.univtln.maxremvi.Model.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class SignInViewController {

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
        try{
            ViewUtil.switchView("register");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void handleCreatePollButtonClick(ActionEvent actionEvent) {

    }
}
