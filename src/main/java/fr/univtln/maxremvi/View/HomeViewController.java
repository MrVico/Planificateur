package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.Poll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class HomeViewController {
    private final ObservableList<HBox> lines = FXCollections.observableArrayList();
    private PollController pollController;

    public void initialize(){
        pollController = new PollController();
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
    public void handleCreatePollButtonClick(ActionEvent actionEvent) {
        System.out.println("Switch view --> Create Poll");
    }
}
