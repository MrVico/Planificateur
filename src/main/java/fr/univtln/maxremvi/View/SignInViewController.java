package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Utils.ViewUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.event.ActionEvent;

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

    @FXML
    public void handleCreatePollButtonClick(ActionEvent event) {
        try{
            ViewUtil.switchView("create_poll");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
