package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Controller.App;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = App.getStage();
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1, 800, 600));
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
