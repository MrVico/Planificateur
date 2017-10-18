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
import jdk.nashorn.internal.scripts.JO;
import sun.nio.cs.US_ASCII;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateProfilViewController {
    @FXML
    private Text login;
    @FXML
    private Text email;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;

    private PersonController personController;

    public void initialize(){
        personController = PersonController.getInstance();
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
            if(personController.updatePerson(p)) {
                AlertManager.AlertBox(Alert.AlertType.INFORMATION, "ed", null, "Profil mis à jour avec succès.");
                ViewManager.switchView("viewProfil");
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
                int dialogNewPassword = JOptionPane.showConfirmDialog(null,passwordField,"Saisir un nouveau mot de passe",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                String newPassword=PasswordManager.encrypt(new String(passwordField.getPassword()));
                if(dialogNewPassword==JOptionPane.OK_OPTION)
                {
                    int dialogNewPasswordConfirmed = JOptionPane.showConfirmDialog(null,passwordField,"Confirmer le nouveau mot de passe",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    String newPasswordConfirmed=PasswordManager.encrypt(new String(passwordField.getPassword()));
                    if(dialogNewPasswordConfirmed==JOptionPane.OK_OPTION)
                    {
                        if(newPassword.compareTo(newPasswordConfirmed)==0)
                        {
                            try{
                                Person p = User.getUser();
                                p.setPassword(newPassword);
                                if(personController.updatePerson(p)) {
                                    AlertManager.AlertBox(Alert.AlertType.INFORMATION, "ed", null, "Profil mis à jour avec succès.");
                                    ViewManager.switchView("viewProfil");
                                }
                                System.out.println("yes");
                            }catch (SQLException e){

                            }

                        }
                        else{
                            System.out.println("no");
                        }

                    }
                }



            }
            else{
                System.out.println("no");
            }

        }

    }



}
