package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class ViewProfilViewController implements ViewControllerInterface {
    @FXML
    private Text login;
    @FXML
    private Text email;
    @FXML
    private Text firstname;
    @FXML
    private Text lastname;

    public void initialize(){
        Person p = User.getUser();
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
        ViewManager.switchView(ViewManager.viewsEnum.UPDATE_PROFIL);
    }

    @Override
    public void initData(Object... arguments) {

    }
}
