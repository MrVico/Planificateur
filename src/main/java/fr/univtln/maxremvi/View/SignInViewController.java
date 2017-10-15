package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Utils.ViewUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    public void handleCreatePollButtonClick(ActionEvent actionEvent) {

    }
}
