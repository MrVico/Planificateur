package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.PasswordManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.*;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInViewController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    private PersonController personController;

    public void initialize() {
        personController = PersonController.getInstance();
    }

    @FXML
    public void handleSignInButtonClick(ActionEvent event) {
        if (login.getText().isEmpty() || password.getText().isEmpty()) {
            AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Veuillez renseigner les champs !");
        } else {
            Person person = personController.getPerson(login.getText(), PasswordManager.encrypt(password.getText().toString()));
            if (person != null) {
                User.setUser(person);
                ViewManager.switchView("home");
            } else {
                AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Mauvais identifiants");
            }
        }
    }

    @FXML
    public void handleRegisterButtonClick(ActionEvent event) {
        ViewManager.switchView("register");

    }

    @FXML
    public void handleHomeButtonClick(ActionEvent event) {
        ViewManager.switchView("home");
    }

    @FXML
    public void handleCreatePollButtonClick(ActionEvent event) {
        ViewManager.switchView("create_poll");
    }
}
