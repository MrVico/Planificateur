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
import java.sql.SQLException;

public class RegisterViewController implements ViewControllerInterface {
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

    /**
     * Handles the actions done on the register button.
     * Calls the signUp function.
     *
     * @param  event  the type of action that was performed
     */
    public void handleRegisterButtonClick(ActionEvent event) {
        signUp();
    }

    /**
     * Signs an user up if he has filled in all the information needed.
     */
    private void signUp(){
        if(login.getText().isEmpty() || password.getText().isEmpty() || conf_password.getText().isEmpty() || email.getText().isEmpty() || firstname.getText().isEmpty() || lastname.getText().isEmpty()){
            AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Tous les champs sont obligatoires");
        }
        else {
            if (!password.getText().equals(conf_password.getText())) {
                AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Les mots de passe ne sont pas identiques.");
            } else {
                if (EmailManager.emailValidator(email.getText())) {
                    try {
                        PersonController.getInstance().addPerson(login.getText(), PasswordManager.encrypt(password.getText()), email.getText(), firstname.getText(), lastname.getText());
                        AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Votre compte a bien été créé !");
                        ViewManager.switchView(ViewManager.viewsEnum.SIGNIN);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Un compte avec ce login ou cet email existe déjà.");
                    }
                } else {
                    AlertManager.AlertBox(AlertType.INFORMATION, "Information", null, "Veuillez renseigner un email valide.");
                }
            }
        }
    }

    /**
     * Handles the actions done on the cancel button.
     * Switches to the SignIn window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleCancelButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.SIGNIN);
    }

    @Override
    public void initData(Object... arguments) {

    }
}
