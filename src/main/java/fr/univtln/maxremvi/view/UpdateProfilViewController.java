package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

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
}
