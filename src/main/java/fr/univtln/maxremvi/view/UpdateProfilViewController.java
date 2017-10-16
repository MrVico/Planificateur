package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ViewUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import javax.swing.text.View;
import java.io.IOException;
import java.sql.SQLException;

/*
    TODO : Prendre la personne connectée
 */
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
        Person p = personController.getPerson("Login");
        login.setText(p.getLogin());
        email.setText(p.getEmail());
        firstname.setText(p.getFirstname());
        lastname.setText(p.getLastname());
    }

    public void handleUpdateProfilButtonClick(ActionEvent actionEvent) {
        try {
            Person p = personController.getPerson("Login");
            p.setFirstname(firstname.getText());
            p.setLastname(lastname.getText());
            if(personController.updatePerson(p)) {
                AlertManager.AlertBox(Alert.AlertType.INFORMATION, "ed", null, "Profil mis à jour avec succès.");
                try {
                    ViewUtil.switchView("viewProfil");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
