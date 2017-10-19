package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.EmailManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
    TODO : Insertion dans la table Invitation & Envoi des mails !
 */
public class SharePollViewController {
    private final ObservableList<HBox> lines = FXCollections.observableArrayList();
    private List<Person> personList;
    @FXML
    private ListView listView;

    public void initialize(){
        try {
            personList = PersonController.getInstance().getPersons();
            List<HBox> hBoxes = new ArrayList<>();
            if(personList != null){
                for(Person person : personList){
                    HBox hBox = new HBox();
                    Label labelA = new Label(person.getFirstname());
                    labelA.setPrefWidth(140);
                    Label labelB = new Label(person.getLastname());
                    labelB.setPrefWidth(140);
                    Label labelC = new Label(person.getEmail());
                    labelC.setPrefWidth(210);
                    CheckBox checkBox = new CheckBox();
                    hBox.getChildren().addAll(labelA, labelB, labelC, checkBox);
                    hBoxes.add(hBox);
                }
                lines.addAll(hBoxes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<HBox> getLines() {
        return lines ;
    }

    public void handleShareButtonClick(ActionEvent actionEvent) {
        List<Person> receivers = new ArrayList<>();
        int index = 0;
        for(HBox hBox : lines){
            CheckBox checkbox = (CheckBox) hBox.getChildren().get(3);
            if(checkbox.isSelected()){
                System.out.println(((Label)hBox.getChildren().get(2)).getText()+" "+index);
                receivers.add(personList.get(index));
            }
            index++;
        }
        EmailManager.sendEmails(receivers);
    }
}
