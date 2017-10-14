package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Controller.PersonController;
import fr.univtln.maxremvi.Utils.AlertManager;
import fr.univtln.maxremvi.Utils.EmailManager;
import fr.univtln.maxremvi.Model.Person;
import fr.univtln.maxremvi.Utils.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterViewController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField conf_password;
    @FXML
    private TextField email;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;

    private PersonController personController;

    public void initialize(){
        personController = new PersonController();
    }

    @FXML
    public void handleButtonRegisterClick(ActionEvent event) {
        signUp();
    }

    // renvoie une personne, du coup la vue connait le modèle :-/
    public Person signUp(){
        if(login.getText().isEmpty() || password.getText().isEmpty() || conf_password.getText().isEmpty() || email.getText().isEmpty()){
            AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Les champs en 'IDENTIFICATEUR' doivent obligatoirement être renseignés.");
        }
        else {
            if (!password.getText().equals(conf_password.getText())) {
                AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Les mots de passe ne sont pas identiques.");
            } else {
                if (EmailManager.emailValidator(email.getText())) {
                    try {
                        Person newPerson = personController.addPerson(login.getText(), PasswordManager.encrypt(password.getText()), email.getText(), firstname.getText(), lastname.getText());
                        AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Votre compte a bien été créé !");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Un compte avec ce login ou cet email existe déjà.");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                } else {
                    AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Veuillez renseigner un email valide.");
                }
            }
        }
        return null;
    }
}
