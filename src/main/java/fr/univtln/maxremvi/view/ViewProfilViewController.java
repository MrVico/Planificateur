package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ViewProfilViewController {
    @FXML
    private TextField login;
    @FXML
    private TextField email;
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

    public void handleModifyProfilButtonClick(ActionEvent actionEvent) {
        System.out.println("Modification du profil demandée");
    }
}
