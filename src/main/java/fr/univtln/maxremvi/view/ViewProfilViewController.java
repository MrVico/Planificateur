package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class ViewProfilViewController {
    @FXML
    private Text login;
    @FXML
    private Text email;
    @FXML
    private Text firstname;
    @FXML
    private Text lastname;

    private PersonController personController;

    public void initialize(){
        personController = PersonController.getInstance();
        Person p = personController.getPerson("Login");
        fillText(login, p.getLogin());
        fillText(email, p.getEmail());
        fillText(firstname, p.getFirstname());
        fillText(lastname, p.getLastname());
    }

    public void fillText(Text input, String value){
        if(value == null) {
            input.setText("Non renseigné");
            input.setFont(Font.font(input.getFont().getName(), FontPosture.ITALIC, input.getFont().getSize()));
        }
        else
            input.setText(value);
    }

    public void handleUpdateProfilButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView("updateProfil");
    }
}
