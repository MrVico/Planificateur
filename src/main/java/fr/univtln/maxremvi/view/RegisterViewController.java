package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.EmailManager;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.utils.PasswordManager;
import fr.univtln.maxremvi.utils.ViewManager;
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
        personController = PersonController.getInstance();
    }

    @FXML
    public void handleRegisterButtonClick(ActionEvent event) {
        signUp();
    }

    public void signUp(){
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
                        ViewManager.switchView("signin");
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
    }

    @FXML
    public void handleCancelButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView("signin");
    }
}
