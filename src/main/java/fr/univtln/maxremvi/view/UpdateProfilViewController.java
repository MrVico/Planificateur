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
import java.sql.SQLException;

public class UpdateProfilViewController implements ViewControllerInterface {
    @FXML
    private Text login;
    @FXML
    private Text email;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;

    public void initialize(){
        Person p = User.getUser();
        login.setText(p.getLogin());
        email.setText(p.getEmail());
        firstname.setText(p.getFirstname());
        lastname.setText(p.getLastname());
    }

    public void handleUpdateProfilButtonClick(ActionEvent actionEvent) {
        try {
            Person p = User.getUser();
            p.setFirstname(firstname.getText());
            p.setLastname(lastname.getText());
            if(PersonController.getInstance().updatePerson(p)) {
                AlertManager.AlertBox(Alert.AlertType.INFORMATION, null, null, "Profil mis à jour avec succès.");
                ViewManager.switchView(ViewManager.viewsEnum.VIEW_PROFIL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                            try{
                                Person p = User.getUser();
                                p.setPassword(newPassword);
                                if(PersonController.getInstance().changePassword(p)) {
                                    AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Profil mis à jour avec succès.");
                                    ViewManager.switchView(ViewManager.viewsEnum.VIEW_PROFIL);
                                }
                            }catch (SQLException e){

                            }

                        }
                        else{
                            AlertManager.AlertBox(Alert.AlertType.INFORMATION,null,null,"Les mots de passes ne sont pas identiques");
                        }

                    }
                }
            }
            else{
                AlertManager.AlertBox(Alert.AlertType.INFORMATION,null,null,"Le mot de passe ne correspond pas à l'utilisateur");
            }
        }
    }

    public void handleRetourClick(ActionEvent actionEvent)
    {
        ViewManager.switchView(ViewManager.viewsEnum.VIEW_PROFIL);
    }


    @Override
    public void initData(Object... arguments) {

    }
}
