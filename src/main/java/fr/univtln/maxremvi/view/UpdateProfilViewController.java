package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.PasswordManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import javax.swing.*;

public class UpdateProfilViewController implements ViewControllerInterface {
    @FXML
    private Text login;
    @FXML
    private Text email;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;

    /**
     * Initializes the UpdateProfil window.
     */
    public void initialize(){
        Person p = User.getUser();
        login.setText(p.getLogin());
        email.setText(p.getEmail());
        firstname.setText(p.getFirstname());
        lastname.setText(p.getLastname());
    }

    /**
     * Handles the actions done on the update profil button.
     * Updates the user's profil.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleUpdateProfilButtonClick(ActionEvent actionEvent) {
        Person p = User.getUser();
        p.setFirstname(firstname.getText());
        p.setLastname(lastname.getText());
        if(PersonController.getInstance().updatePerson(p)) {
            AlertManager.alertBox(Alert.AlertType.INFORMATION, null, null, "Profil mis à jour avec succès.");
            ViewManager.switchView(ViewManager.viewsEnum.VIEW_PROFIL);
        }
        else {
            AlertManager.printError();
        }
    }

    /**
     * Handles the actions done on the change password button.
     * Changes the user's password.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleChangePasswordButtonClick(ActionEvent actionEvent)
    {
        JPasswordField passwordField=new JPasswordField();
        int dialogPassword = JOptionPane.showConfirmDialog(null,passwordField,"Saisir mot de passe",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String password=PasswordManager.encrypt(new String(passwordField.getPassword()));
        if(dialogPassword==JOptionPane.OK_OPTION)
        {
            if(User.getUser().getPassword().compareTo(password)==0)
            {
                passwordField.setText("");
                int dialogNewPassword = JOptionPane.showConfirmDialog(null,passwordField,"Saisir un nouveau mot de passe",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                String newPassword=PasswordManager.encrypt(new String(passwordField.getPassword()));
                if(dialogNewPassword==JOptionPane.OK_OPTION)
                {
                    passwordField.setText("");
                    int dialogNewPasswordConfirmed = JOptionPane.showConfirmDialog(null,passwordField,"Confirmer le nouveau mot de passe",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    String newPasswordConfirmed=PasswordManager.encrypt(new String(passwordField.getPassword()));
                    if(dialogNewPasswordConfirmed==JOptionPane.OK_OPTION)
                    {
                        if(newPassword.compareTo(newPasswordConfirmed)==0)
                        {
                            Person p = User.getUser();
                            p.setPassword(newPassword);
                            if(PersonController.getInstance().changePassword(p)) {
                                AlertManager.alertBox(Alert.AlertType.INFORMATION, "Information", null, "Mot de passe mis à jour.");
                                ViewManager.switchView(ViewManager.viewsEnum.VIEW_PROFIL);
                            }
                            else{
                                AlertManager.printError();
                            }
                        }
                        else{
                            AlertManager.alertBox(Alert.AlertType.INFORMATION,null,null,"Les mots de passes ne sont pas identiques.");
                        }
                    }
                }
            }
            else{
                AlertManager.alertBox(Alert.AlertType.INFORMATION,null,null,"Le mot de passe ne correspond pas à l'utilisateur.");
            }
        }
    }

    /**
     * Handles the actions done on the return button.
     * Switches to the ViewProfil window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleReturnButtonClick(ActionEvent actionEvent)
    {
        ViewManager.switchView(ViewManager.viewsEnum.VIEW_PROFIL);
    }


    @Override
    public void initData(Object... arguments) {

    }
}
