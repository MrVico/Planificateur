package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.model.AnswerChoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ViewPollViewController {

    @FXML
    private TableView<AnswerChoice> pollDateTable;


    public static void useless(){
        //PollDate useless = new PollDate();
    }

    @FXML
    protected void addPollDate(ActionEvent event) {

        /*
        ObservableList<PollDate> dates = pollDateTable.getItems();
        dates.add(new PollDate(firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText()
        ));

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        */
    }
}
