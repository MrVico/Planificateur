package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class HomeViewController implements ViewControllerInterface {
    private final ObservableList<HBox> lines = FXCollections.observableArrayList();
    private List<Poll> pollList;
    private PollController pollController;
    @FXML
    private ListView listView;

    public void initialize(){
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==2){
                    Poll selectedPoll = pollList.get(listView.getSelectionModel().getSelectedIndex());
                    ViewManager.switchView("viewPoll", selectedPoll);
                }
            }
        });
        pollController = PollController.getInstance();
        try {
            pollList = pollController.getPolls();
            List<HBox> hBoxes = new ArrayList<>();
            if(pollList != null){
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<HBox> getLines() {
        return lines ;
    }

    @FXML
    public void handleCreatePollButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView("createPoll");
    }

    public void handleViewProfilButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView("viewProfil");
    }

    @Override
    public void initData(Object... arguments) {

    }
}
