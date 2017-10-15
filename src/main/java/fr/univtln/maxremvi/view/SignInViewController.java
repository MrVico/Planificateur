package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.utils.ViewUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInViewController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    @FXML
    public void handleSignInButtonClick(ActionEvent event){
        if(login.getText().isEmpty() || password.getText().isEmpty() )
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez renseigner les champs");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Super vous allez être authentifié");
            alert.showAndWait();
        }



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
    public void handleHomeButtonClick(ActionEvent event) {
        try {
            ViewUtil.switchView("home");
        } catch (IOException e) {
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
