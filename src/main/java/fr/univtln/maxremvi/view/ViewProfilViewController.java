package fr.univtln.maxremvi.view;

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

    /**
     * Initializes the ViewProfil window.
     */
    public void initialize(){
        Person p = User.getUser();
        fillText(login, p.getLogin());
        fillText(email, p.getEmail());
        fillText(firstname, p.getFirstname());
        fillText(lastname, p.getLastname());
    }

    /**
     * Writes the value into the text input if the value isn't empty.
     * If it's empty it will write "Non renseigné" instead.
     *
     * @param  input  the input into which the value should go
     * @param  value  the value of the input
     */
    private void fillText(Text input, String value){
        if(value == null) {
            input.setText("Non renseigné");
            input.setFont(Font.font(input.getFont().getName(), FontPosture.ITALIC, input.getFont().getSize()));
        }
        else
            input.setText(value);
    }

    /**
     * Handles the actions done on the update profil button.
     * Switches to the UpdateProfil window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleUpdateProfilButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.UPDATE_PROFIL);
    }

    /**
     * Handles the actions done on the return button.
     * Switches to the Home window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleReturnButtonClick(ActionEvent actionEvent)
    {
        ViewManager.switchView(ViewManager.viewsEnum.HOME);
    }

    @Override
    public void initData(Object... arguments) {

    }
}
