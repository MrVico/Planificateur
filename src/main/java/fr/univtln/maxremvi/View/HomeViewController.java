package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Controller.PersonController;
import fr.univtln.maxremvi.Controller.PollController;
import fr.univtln.maxremvi.Model.Person;
import fr.univtln.maxremvi.Model.Poll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeViewController {
    private final ObservableList<HBox> lines = FXCollections.observableArrayList();
    private PollController pollController;

    public void initialize(){
        PersonController personController = new PersonController();
        Person promoter = personController.getPerson("ddzdzd");

        pollController = new PollController();
        try {
            pollController.addPoll("test", "desc", "bordeaux", Calendar.getInstance().getTime(), false, promoter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<Poll> pollList = pollController.getPolls();
            List<HBox> hBoxes = new ArrayList<>();
            for(Poll poll : pollList){
                HBox hBox = new HBox();
                Label labelA = new Label(poll.getTitle());
                labelA.setPrefWidth(150);
                Label labelB = new Label(poll.getPromoter().getLogin());
                labelB.setPrefWidth(150);
                Label labelC = new Label(poll.getClosingDate().toString());
                labelC.setPrefWidth(150);
                hBox.getChildren().addAll(labelA, labelB, labelC);
                hBoxes.add(hBox);
            }
            lines.addAll(hBoxes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<HBox> getLines() {
        return lines ;
    }

    @FXML
    public void handleClick(ActionEvent actionEvent){
        System.out.println("CLICK");
        //panel.getChildren().add(new TextField("Please fill in..."));
    }
}
