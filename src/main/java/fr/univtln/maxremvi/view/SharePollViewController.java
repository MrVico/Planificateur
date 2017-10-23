package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.App;
import fr.univtln.maxremvi.controller.InvitationController;
import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Invitation;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.AlertManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    TODO : Insertion dans la table Invitation & Envoi des mails !
 */
public class SharePollViewController implements ViewControllerInterface{
    private final ObservableList<HBox> lines = FXCollections.observableArrayList();
    private List<Person> personList;
    private Poll poll;
    @FXML
    private ListView listView;
    @FXML
    private Button shareButton;

    public void initialize(){
        if(poll != null){
            try {
                personList = PersonController.getInstance().getNotInvitedToPoll(poll.getID(), User.getUser().getID());
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
                receivers.add(personList.get(index));
            }
            index++;
        }

        List<Invitation> invitations = new ArrayList<>();
        for(Person person : receivers){
            invitations.add(new Invitation( poll.getID(),person.getID(), User.getUser().getID(), false));
        }

        try {
            InvitationController.getInstance().addAll(invitations);
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information",null, "Partage effectué.");
            ((Stage)shareButton.getScene().getWindow()).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }
}
